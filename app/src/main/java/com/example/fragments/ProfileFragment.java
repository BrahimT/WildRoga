package com.example.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.example.model.LoggedInUser;
import com.example.myapplication.R;
import com.example.pages.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.List;
import java.util.Objects;

public class ProfileFragment extends Fragment {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private LoggedInUser userRef;

    private Button signoutButton;
    private TextView tvName;
    private TextView tvUpdateEmail;
    private TextView tvResetPassword;
    private TextView tvDeleteAccount;

    public ProfileFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = mAuth.getCurrentUser();
        signoutButton = view.findViewById(R.id.sign_out_button);
        tvName = view.findViewById(R.id.name_text);
        tvUpdateEmail = view.findViewById(R.id.update_email);

        if (user == null) {
            this.startActivity(new Intent(getActivity(), LoginActivity.class));
        } else {
            db.collection("users")
                    .whereEqualTo("userId", user.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                       if (task.isSuccessful()) {
                           List<LoggedInUser> users = task.getResult().toObjects(LoggedInUser.class);

                           if (users.size() == 1) {
                               userRef = users.get(0);
                               userRef.setDocumentId(task.getResult().getDocuments().get(0).getId());

                               String firstName = userRef.getDisplayName();
                               Resources res = getResources();
                               String formattedString = res.getString(R.string.user_name, firstName);
                               tvName.setText(formattedString);
                           }
                       }
                    });
        }

        signoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

        tvUpdateEmail.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setView(R.layout.alert_change_email)
                    .setMessage(R.string.message_change_email)
                    .setTitle(R.string.action_change_email)
                    .setPositiveButton(R.string.action_save_email, (dialog, which) -> {
                        Dialog d = (Dialog) dialog;
                        TextInputEditText etChangeEmail = d.findViewById(R.id.alert_email_field);
                        String oldEmail = user.getEmail();
                        String newEmail = etChangeEmail.getText().toString().toLowerCase();

                        user.updateEmail(newEmail).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                db.collection("users").document(userRef.getDocumentId()).update("email", newEmail)
                                        .addOnSuccessListener(aVoid -> {
                                            Log.d("COLLECTION", "Document updated successfully");
                                        });

                                assert oldEmail != null;
                                DocumentReference fromPath = db.collection("salts").document(oldEmail);
                                DocumentReference toPath = db.collection("salts").document(newEmail);

                                moveFireStoreDoc(fromPath, toPath);

                                user.sendEmailVerification().addOnCompleteListener(task1 -> {
                                    if (task.isSuccessful()) {
                                        Log.d("EMAIL", "Email sent");
                                    } else if (!task.isSuccessful()) {
                                        Log.d("EMAIL Exception", Objects.requireNonNull(task.getException()).getMessage());
                                    }
                                });

                                Toast.makeText(requireContext(), R.string.alert_email_changed, Toast.LENGTH_SHORT).show();
                            } else if (!task.isSuccessful()) {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthRecentLoginRequiredException e) {
                                    //TODO re-authenticate user if required.
                                } catch (Exception e) {
                                    Log.d("EXCEPTION: ", e.getMessage());
                                }
                            }
                        });
                    })
                    .show();
        });
    }

    public void moveFireStoreDoc(DocumentReference fromPath, DocumentReference toPath) {
        fromPath.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    toPath.set(document.getData())
                            .addOnSuccessListener(aVoid -> {
                                Log.d("NEW", "DocumentSnapshot successfully written!");
                                fromPath.delete()
                                        .addOnSuccessListener(aVoid1 -> Log.d("DELETE", "DocumentSnapshot successfully deleted!"))
                                        .addOnFailureListener(e -> Log.w("ERRORDELETE", "Error deleting document", e));
                            })
                            .addOnFailureListener(e -> Log.w("ERRORWRITE", "Error writing document", e));
                } else {
                    Log.d("DOCNOTFOUND", "No such document");
                }
            } else {
                Log.d("EXCEPTION", "get failed with ", task.getException());
            }
        });
    }
}
