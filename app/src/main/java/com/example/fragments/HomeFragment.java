package com.example.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Video;
import com.example.myapplication.R;
import com.example.tools.VideoViewAdapter;
import com.example.tools.VideoViewListener;
import com.potyvideo.library.AndExoPlayerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements VideoViewListener {
    private RecyclerView hView;
    private VideoViewAdapter hAdapter;
    private RecyclerView.LayoutManager hManager;
    private Video v;
    private List<Video> videos;
    private AndExoPlayerView videoPlayerView;
    private TextView videoTitle;

    public HomeFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        v = new Video();

        hView = view.findViewById(R.id.recycler_home);
        videoPlayerView = view.findViewById(R.id.videoPlayerView);
        videoTitle = view.findViewById(R.id.videoTitle);

        hManager = new LinearLayoutManager(getActivity());
        hView.setLayoutManager(hManager);

        hAdapter = new VideoViewAdapter(getContext(),loadVideos());
        hAdapter.videoViewListener = this;

        hView.setAdapter(hAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        videoPlayerView.setSource(videos.get(0).getVideoURL());
        videoTitle.setText(videos.get(0).getTitle());
    }

    //TEMPORARY METHOD TO LOAD VIDEOS TO LIST | TESTING
    public List<Video> loadVideos() {
        videos = new ArrayList<>();


        videos.add(new Video(1,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AADVemggp3pz-Qb1Gu89SzMMa?dl=0&preview=AllLevelsFlow.mp4"
                , null, "AllLevelsFlow", "https://uca69d3cbc6c03e5b3b892bbc1fb.previews.dropboxusercontent.com/p/thumb/ABGBHC97vIAR5NdJF4tv6oPIfwsp0Th2x90-JQFZItazbzaYWWXwhmkmLhDNrEXKEQt6B8pLAzyzn-9RqRvmQXjIm8hNQiArXLi4TPqls-iSbBAoTakbHNrUjCeWLCeUEnAAi_9Ge4MNQTVFBBEY3YrhjV-E34aCjsVRouQqG0_b8pnfspA5GKwwJcvpEzuwx7tDJHOurTpbd-6ylIYKTUTNwsVsiQX92fPfNtk3en3SGLjjT9-8qmFL0VCs7jNkNvE1CT57-c030AcKQoxJ1Ur8Ag7b6AuwrbQbe6j05wCc6w/p.jpeg?size=178x178&size_mode=1"));
        videos.add(new Video(2,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AAAg2C39Pw4jBIma5fu9wOLSa/Bootcamp%201.mp4?dl=0",
                null, "Bootcamp 1", "https://uccc7cea05b192cc703800379284.previews.dropboxusercontent.com/p/thumb/ABFnosjJil5qESDFxYfL7kRWYpIiEmfqiyNmDQwB0JZNYyQsTHu-GgcVQOCjiXdWpqDJFZvf4hbPCZKQyYSF9R64BNfl-4fo2gfhWpdTRx4xTFFGXaQ-1M8f9BVwA1EaiwXhsW49gESIPTYImIRZ_65bfdY8Vi6G3BAJAGV1Rr_g5Pw_PaJruqGtjX7gO1jXNL-rfu6cK6Fv8IcjEsxwnTb0s6Z0SC9xCEI5HeYIzPeeNi5wUDp7LnvWy59pmL9kL-Hfp0GNNLDnwYKVl_jHfMbDXyr-IBdOMBpXtKLZFkCw3A/p.jpeg?size=178x178&size_mode=1"));
        videos.add(new Video(3,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AAAmOl16939Dp-0DRdzbJ-V8a/Bootcamp%202.mp4?dl=0"
                , null, "Bootcamp 2", "https://ucda54f2840c05556eaa07c62d39.previews.dropboxusercontent.com/p/thumb/ABHuigEnmYVMTetR6eckd6nfLfVN38oDeYZWPWoQBPajJj1lN-0CGvcMoElwb6Ibc-0d7Ru2993nn7FUUJq9TofhOgmnBe9R8Ghfp1MtT5RzeGTigWUhoXkunLIqMPpnlcybrYzIl1JJ-iDgs9rYLp8TG62AmJCJq4kOy1j-bDVUgNj8IivlijLMeBOglM9LcVRNYJ3RkyqghtyV697u2d_oYYkjcG3p5wWD7EV0OHjz0lzrIF_fwc9NC-nPkzTSlaefw-SCp3DwPCFKIreLBRtAb8Q9lzcTU7VRwLazXCYI_Q/p.jpeg?size=178x178&size_mode=1"));
        videos.add(new Video(4,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AABZSQSLpCD-MHuX6v9QbxU1a/Bootcamp%203.mp4?dl=0"
                , null, "Bootcamp 3", "https://uc64f13a4527d8e0982b00a23fbf.previews.dropboxusercontent.com/p/thumb/ABFQHEm_pnlqiqhVPtcDRIibEnwd9dZZZ0DFYwxoEIxXjv4-vRl0wNb3_Oc19MW2q7U-ObDXBc915RbYRje8FQAmN1MmvOjkTvFHNWaUQaiM9kOfJJ3TLuJ70FKJUU5OkqbquLtFTNwMM9fhHCeJsYpIuYREII0MhH6wDrJt7taOQ4YEVffBuZSyBvteRc_B8WwpR6zZnLD28eqKz7nwWkHRugp1apM0dTrCbkMkkf6kRn9hwJQm3QyNcQWCywX1hpwuUm_gwHyh2Ig1bu98ABJ1WOguPQG91IQX-hSNUAuP4g/p.jpeg?size=178x178&size_mode=1"));
        videos.add(new Video(5,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AABurIhKJnVTrhfMpS9y7e17a/Bootcamp.mp4?dl=0"
                , null, "Bootcamp", "https://ucef80a859d217a3bfca7d05067f.previews.dropboxusercontent.com/p/thumb/ABGvyqPHUGs6NS6Tjcksrl-8BffICYi4nYq_9k4d40qGZOyUDJrQiIWsHy4UzvzVTmwLf4SGlRdoWX9u28AF_YOpoH8MTTiRn8rPUwIe3MsxJ-rURri0FrNOejfcz8v-Gnm8kbDqJwvmL7RWWc7TMOgytFWIi2sZb7p1M17D6j1QYIZ01xNUYZRQLz79WXvGbr8M0C88cWoVxzw06iaUOQ-xwx82-ViqyVoUiADZ3dMJOm6jdZ7qDGLz6jztIpzIRW_0fcMjkEk_xR8K8iETlG-vBblSNDacUCdMPFGk3x79Yg/p.jpeg?size=178x178&size_mode=1"));
        videos.add(new Video(6,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AAB6jSA2Do-hrlX_c6ZEBq2Ua/Couch%20Session.mp4?dl=0"
                , null, "Couch Session", "https://uc0777ec044d36e0b5ab485b0e56.previews.dropboxusercontent.com/p/thumb/ABE_EAUuZET8K61E4CXo2Ef-2plvZul3CGRMn4mTbLHJe-A92sUMTl7qzAZklP7ROzHx0F7JP6fF15n8L7wIrEYux60HJWBI1__GBuO2dmGDIHBTrb7bPSjs6Fq3AN3F3qouTTPvaBAwtOJCs1LMoNH73tNThZgGX9rCaV2pLe2wamuBrcz08ZRwn3mjRxbD_RIinigWljnOhiNZWn7CufdFxAvhfw4OaIP437K3R6t3qocHUUbMmP5CikLErRklQ0aQVB4PKT4YxR56rOVSSm5Oh9oFGtowSCuf57lZ9whATg/p.jpeg?size=178x178&size_mode=1"));
        videos.add(new Video(7,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AACwdS_KR8nzGue-ZBJgRk5Ga/Easy%20Flow.mp4?dl=0",
                null, "Easy Flows", "https://uce0476894cbbf7d8bc11f81e485.previews.dropboxusercontent.com/p/thumb/ABEsv0EJO0nQQGKs4Pi4m89VhqJ7Lc8kJjlqNqqiDJBAZ6ATa-Wl3rzFuB_LdEzN9mW_R9-2xWUOA5r9v2_HyhxZuaSMNwcV1GAiIYkz-lmbMbfgKe8UWA1SZjZlpyhmUjE6iEI09SoBN4nvPTinaQzYGqXWLh-7jTCH_L_DceaYWIaN1wTlXshays1N6z2jspiJ8f0ODmyN-p9I6d6V9HQ8vh1uWucZx73C_dxdd7gMj1LqeoplAoVS5GuEjGpbi_zlNo89gV2yyZhJBKzFuPfCPfc9S-ofWEGB59J4RDcEsA/p.jpeg?size=178x178&size_mode=1"));
        videos.add(new Video(8,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AABXGYTYS3LYMUjgdsOPT1Yya/Live%20Flow.mp4?dl=0"
                , null, "Live Flow", "https://uc9736ba591f3513589ad923d044.previews.dropboxusercontent.com/p/thumb/ABGrTH-hPWku_X79ZqFjSKokdweqqCuXVVKdpDefvnL_AcjZSBEtahh3Q36vYVxEJKfBkOLNDAAF-5-FBMqDYrg8a-iT7LRjzON_AjagJSx-yOlgrcURDcnFI-I4--BNAu4HERo-AEMpO-BCUj85zbJzT6By362RE56i5FohVHVJ0SIoQuIiHyWR9E6ste3MCoWbhBtf2gGgcGPj7SBHfHVen6DT6on67hI7zwMas3GXfE0iGlkm8W8UML6_tswmbS--F9lGTW8eA9X9veyoAyaG66PCqlM2X5cUKj6iaU0cdg/p.jpeg?size=178x178&size_mode=1"));
        videos.add(new Video(9,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AABo17AzIvRsO1yEktYu5Vpwa/Power%20%26%20Chill.mp4?dl=0"
                , null, "Power & Chill", "https://uc41edf3ccfe951b63e7bc2244ee.previews.dropboxusercontent.com/p/thumb/ABEqGrjnfxs0QuXClNKzJkupDUuNq1jN0Jw0-gXGaUOKkfFuGsLhlqG5azTAqV8Cj1cEET2Hv7LJm_9nH3dGe4uK_8Z3X5qTgvWb5LJxldETnlZIvznIlY9IJma2hSnYRJPoPMGX4Quz3a_FSYN2S16P7qQxWgHLOWn8HR4CUUHqvgzKiLBK0hdf-DOWHHpDr29qYGGnzDvIQocpsNumaXjh79-aNPdrHAQwPMKzZFsPoGD0WkpcwdhlvXDlPJS3oQhZ_B3p58RYklm11Xa0BKWbj00VkuMxXiNYvklvPGHhWA/p.jpeg?size=178x178&size_mode=1"));
        videos.add(new Video(10,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AACCxgN-gdK3DBX9_Kcuzdqla/Power%20and%20Chill.mp4?dl=0",
                null, "Power and Chill", "https://uce2ff2d432a8cf0079f9f75da84.previews.dropboxusercontent.com/p/thumb/ABE5AFudHhRKiec530-3Lx7TofxFX-L78XKgdj_37oggbFRgTl_sMDt4N7w7P_JwT3NpANyeQO1mfhOKE-8ua6wJlxK0H7cEYbfBxlVQzD6rIcXK34Pt_EfwU9z36PIkZMd80VM3b1G8GPXjlv5PyRPNAAHo9ipyL8Av1L_NBe9ZZxA-PtvQvvvDb1tO-EWfdQgnCb9XaPHBUl7Vy77mYEhsUjuD7e1LIGXRocR5XRfCMc773GIpcv5_v5r6n3bjureGfv-yJvoIQ7KVctSF3_4OStLnYQpN21bEBP2MhVYb0Q/p.jpeg?size=178x178&size_mode=1"));
        videos.add(new Video(11,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AADRiddruBj6WWj2Qc7731M5a/Power%20and%20ChillSpeed.mp4?dl=0",
                null, "Power and Chill Speed", "https://uca84afa429550630a86b8855d73.previews.dropboxusercontent.com/p/thumb/ABG76Sb9zF7zi4tNDvrFxsnJ_YjaFFkA3KhakmdiuAQkSIqLhokXHf9jGGEwZ16UvHGPAJ9sWEAPikSeM4vmVPwB4o7A-74Bx2ehcFrr4dIkbeXWC1dG60UuV98fNkf6DyprKnDPzPJHtcEa8pZGyr-abtEFkXiZVL3f55NjixJdD3tTiqp5UdcjAyVMSlunLgC68jpcJdjDRmAUdLEzFtiloRZ2K5kOyA0Wo0pJbHnVUjuFuTumTvIzfkOf4lI4ElQBG-Z3BtDtAI96O8iCue5Nznw5D1xcsz1-4E8E6OjdwCuGExMWjPVk0UdbgaLf7jfDYhOLYeCOO8fsUiQFA_UsplTC-b0rbzx5g05dtEnGz9cs5mcPbIV9RDLTo_50E8kiQOM0fSU6qUmq8eJL80GK1DQJXPxq4tt142dm8oF7KzvAPnU9mNY2W1DSs8I1O7AyXIjJi2eCU0YlKINHJT4YEg3jVArlGERjySqKN9FEhLc8_cz929ZByzS823hz0Jd3_xYrRdu4k7-eR2g-QLiA_IstGaCHnjB8o58QKFt48bGkd3iyX3HR7rM0Uvq71q-DIqfoL8o4FZw5MYIO5vTSPsPiiQZfzwkmQkj5pNrrig-NVhgthU2d8TBu21aR3sjuSlcwEapqSsbPjJJUjzCIpkbcWWe2oY1B7AZwYHD4s-6onV8Lh73WQUlshwKeMcW_1myL0mws4XT7ZxMyoG-8krCDuImNfbsbrmb_QQ83IAIcVb-j-_0YfH53uMPrdEPY8Y31CwG-x6rY-ldj4FHGIOeXHGbdzu1LRT4Aez2vfrMDt4Zx-47ZS4OZqfK_Kazouy5irIHCApDtSW7_QTxepaPMIkzWFig8bMtHjGsCeqWl6Axt12_BADJUmGfGZKz2yCIavZcrc5KlogbfNb8VGEC3dBOB8ME2_bqfPxNlwHbJT2HbDBu8qC12ejf_s_44iFB9QHjIZUVvAZajrWumuBOwQisKmYklZil81RF7V2oBUeNCLpeGQRcGvOIYfWx-UB8pChs4bedMnn7FU8vM9_SG2zIsX0uK1cVPWkYZ4L0SL4e2sHdVDK-X5XxZe0e0e8IS3UCIiQCgQ3x2wQ-b_HwOXgx353AgrpNdRZxroghJzdvidmi2P0ZbLB3oooU-US9pI6Zyy4O37kGO-HlN5sGDZmPT6c1hf6kA-51-bh8JjNC39oJfCVusJ4hWL-7MDcv5et_WSgaucpyP6JKrN772tzWfDRgQxpQ_rM0rDV6U5OuXL1jo3K3M7l53FKCskLQUP-63njfRIBBB4TbYbvapexcBCNsD1sr0D4mUhtVH2bYjVn4EjK9-9p1fYGsdYoaLxOcHHb53abJL2F9KwIC14KtDK8wBkUCg_YA7jAieU0wsDQhCCx1mtcSkCzL5Wlf_zu6sOLUSCMVLrtnOYSYj1MZ0et8hDd4FnW5DvQ/p.jpeg?size=178x178&size_mode=1"));
        videos.add(new Video(12,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AAA7TkilVgI7vM4wPNPk5uNva/Quick%20Flow.mp4?dl=0",
                null, "Quick Flow", "https://uc69cad8c95ef359e1b6d78d9ce1.previews.dropboxusercontent.com/p/thumb/ABEv16O-bVtZw1rfaasiAUqX2k-HFuaQ6n-k2BFQaNRQwTtRQqjoA2bUdeY62bMrgModFEgc1BwKS8tAqN0wjTUmyuSiL3FD9Z-PkCh_SAaMxZskxvx2S8uLwECLXdXrs-Dpn3oa1jpiuz4Li7YwNGTyKrsO_JXNusu6IHcIZQ1ptSircNk3ZP9nrCdWXwWzI9DxkbipN4GupBAtOVLv8AXu4_n43dwuHawyppXss5TLQR-BaAt6749jLW4rz03mlmqbikoLMI5xWwbh4fMzVZNHLngy85WY2x-03H6Irqjjgw/p.jpeg?size=178x178&size_mode=1"));
        videos.add(new Video(13,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AAAmF_2z7dWOCS6jIwfHfKOra/Rest.mp4?dl=0",
                null, "Rest", "https://uc48f5ceafadec7091571b7499b7.previews.dropboxusercontent.com/p/thumb/ABEbINDnH5DezIdzUdu2qTCrYfuUWEpnDDR-KJ7NvVMrSu2GA6ugszXCC3gOzeloKsq-pbnYF3dkE48s3AXQ1mH6kD6WMfJWgkiqZv2CsJ081I_7kFVkXKBE8ciMX6YlOBBfx2BpOJ91BoJUhSWqYHfIIVgayqG4bdXx_Y4hAkoL4Mw9szs-eNfj1pj9H1ymv-yfe-KtSHM3wQMcgdp9Wo-eUVGcTtUct9EiAdrEYBK08KQzS0TkzvlOcCCJuKDbrrCppwqGGnyUpUO59azuaJBwYIhzEJ_x5ULR2WS-hgAhMg/p.jpeg?size=178x178&size_mode=1"));
        videos.add(new Video(14,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AACGkAaFjpB6WJ9SG674UaT2a/Ro%20Nwosu%20on%202021-01-01%20at%2023.20.57.mov?dl=0",
                null, "Ro Nwosu", "https://uc412b1c55f9b382ecd234e22133.previews.dropboxusercontent.com/p/thumb/ABHQD4FfPsVeIrXHzD81a0fLTeuszzhucS78qKiiBJsY7HbAmWVTg4aSMRE6ppKMSg_Nx3o8uQu__PVjYxBnDvFk2rp975JgOe2eBva3o1HJuX6A3ECcti1-AYWs8rGE6kbhF0spyUbN44Env128gnkaJghaGV-n5af7k4rRL_hqPlPOpEayK_RWrYmcsXbZH0CfvgkUOV_pKUuAqwUcmxI0W5W4GZQ3zjDdbmlLSOGHCZqKIrlPJ5HhLxNb-N0i4sAuwspz4TMU0zCKCkCBIN5KNW6FJWiPJQY89vsxPxrL72mu6ltK6xWwMxCIyBsWAzfT26GIh3Q3tjFGSsFXSUU6nVtlSX7tc_5PM5aSWQ3NN6FNne-yrHpXj5JQhOZrukZvwNCibIhaiRx4wdpReuAoDLuZIcIrRKtq-5FY1FXZDP10qzTPOTbQSVLMOhXvZL7VOichEajIdwOo3Cw-wUKpO8BEhTDRwdZwxaacUTtda7QUMQ5t48CaZZxFWyEtwfHQadGZdBwfCa5Am8gOUb5Cei_zq2nRpMIR_dBpMn6b52foq4ChEAzEIHI76Z1uf0LovDlf9QcM9PhTLYTDvpskxQB5r1oLG36GNYqSVYBzEK3_ee_qwSDzMDow751b5KJtwQiDueQY2FCuppvICj9c/p.jpeg?size=178x178&size_mode=1"));
        videos.add(new Video(15,"https://www.dropbox.com/sh/zqhs7u4wk8m8fwa/AAC6c4Zma7eyMwykHbGaQUcia/Stretch%20Remix%201.mp4?dl=0",
                null, "Stretch Remix 1", "https://uc6900de153a3119112a62e2018f.previews.dropboxusercontent.com/p/thumb/ABH8J-waTGVbKZbH2sybHRbkRs38c4HcZaT2HuCtRFN1W4Iz9gTXZvfn1_WypgSPv8EAgLRaXUTHftTd4SmwJxi8zLmr9YHmwInZ70uV-swrtF-L0WpbE7fP4_xEF3zz78sIXopobCUby8hOkd2Bk_FezyPcPvn9eTfhITglDwLDoKfmB81Nz5JuUgv-9o5YvgaMTCI0q3oPf1AtWmKqQavjny3dxIlTwfdFqvcLnEKYM9qkEi8udPd4O4IY4dvXXkLXh4eBFTJeJ0ToNVEmIXv4u1w_86pYesFKxVCIgXJ1Fg/p.jpeg?size=178x178&size_mode=1"));


        return videos;
    }

    //TEMPORARY METHOD TO CONVERT DRAWABLE TO BITMAP
    public Bitmap drawableToBitmap(int drawable) {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawable);
        return bm;
    }

    @Override
    public void onVideoClick(Video video) {
        videoPlayerView.setSource(video.getVideoURL());
        videoTitle.setText(video.getTitle());
    }
}
