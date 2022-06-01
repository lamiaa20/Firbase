package com.alqsa.firbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private RecyclerView offerRecyclerView , bestSellerRecyclerView , clothingRecyclerView , bestSellerRecyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users").whereEqualTo("uid",firebaseAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot q :task.getResult()){
                        System.out.println(q.getData());
                    }
                }

            }
        });




        /// offer RecyclerView
        offerRecyclerView = findViewById(R.id.offerRecyclerView);
        offerRecyclerView.setHasFixedSize(true);
        offerRecyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));

        List<Integer> imageList = new ArrayList<>();

        imageList.add(R.drawable.offer_shoping);
        imageList.add(R.drawable.nikon_canon_offer);
        imageList.add(R.drawable.tv_offer);

        OfferAdapter offerAdapter = new OfferAdapter(imageList);

        offerRecyclerView.setAdapter(offerAdapter);

        // best RecyclerView

        bestSellerRecyclerView = findViewById(R.id.bestSellerRecyclerview);
        bestSellerRecyclerView.setHasFixedSize(true);
        bestSellerRecyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));

        List<BestSeller> bestSellerList = new ArrayList<>();

        bestSellerList.add(new BestSeller(R.drawable.bags , "Up to 20% off"));
        bestSellerList.add(new BestSeller(R.drawable.mobiles , "Up to 20% off"));
        bestSellerList.add(new BestSeller(R.drawable.watches , "Up to 20% off"));

        BestSellerAdapter bestSellerAdapter = new BestSellerAdapter(bestSellerList);

        bestSellerRecyclerView.setAdapter(bestSellerAdapter);


        // clothing RecyclerView

        clothingRecyclerView = findViewById(R.id.clothingRecyclerview);
        clothingRecyclerView.setHasFixedSize(true);
        clothingRecyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));

        List<Clothing> clothingList = new ArrayList<>();

        clothingList.add(new Clothing(R.drawable.levis_clothing , "Up to 30% off"));
        clothingList.add(new Clothing(R.drawable.women_clothing , "Up to 30% off"));
        clothingList.add(new Clothing(R.drawable.nikeshoes , "Up to 30% off"));

        ClothingAdapter clothingAdapter = new ClothingAdapter(clothingList);
        clothingRecyclerView.setAdapter(clothingAdapter);

        // best RecyclerView 2

        bestSellerRecyclerView2 = findViewById(R.id.bestSeller2RecyclerView);
        bestSellerRecyclerView2.setHasFixedSize(true);
        bestSellerRecyclerView2.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));

        bestSellerRecyclerView2.setAdapter(bestSellerAdapter);
    }
}