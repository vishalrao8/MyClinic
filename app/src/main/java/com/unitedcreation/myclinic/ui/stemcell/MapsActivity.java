
package com.unitedcreation.myclinic.ui.stemcell;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unitedcreation.myclinic.R;
import com.unitedcreation.myclinic.utils.StringUtils;

import java.util.Objects;
import java.util.Random;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private String name;

    private double latitude, longitude;

    @BindView(R.id.fab_maps_pay)
    FloatingActionButton mPayButton;

    private BottomSheetBehavior<ConstraintLayout> bottomSheet;

    @BindView(R.id.layout_maps_sheet)
    ConstraintLayout bottomSheetLayout;

    @BindView(R.id.iv_maps_down)
    ImageView downButton;

    @BindView(R.id.tv_maps_collapsed)
    TextView mBankName;

    @BindView(R.id.tv_maps_expended)
    TextView mBankName2;

    @BindView(R.id.ratingBar)
    RatingBar mRatingBar;

    @BindView(R.id.layout_maps_expended)
    ConstraintLayout expendedTopBar;

    @BindView(R.id.tv_maps_rating)
    TextView mMapsRating;

    @BindView(R.id.layout_maps_collapsed)
    ConstraintLayout collapsedTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        name = getIntent().getStringExtra(StringUtils.NAME);
        latitude = getIntent().getDoubleExtra(StringUtils.LATITUDE,0);
        longitude = getIntent().getDoubleExtra(StringUtils.LONGITUDE,0);

        ButterKnife.bind(this);
        Random rand = new Random();

        // Obtain a number between [0 - 49].
        int n = rand.nextInt(5);
        mBankName.setText(name);
        mBankName2.setText(name);
        mRatingBar.setRating(n+1);
        mMapsRating.setText(String.valueOf(n+1));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_maps_map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        setUpBottomSheet();

        downButton.setOnClickListener(v -> {

            bottomSheet.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            hideTopBar();

        });

        mPayButton.setOnClickListener(v -> moveToPayment());
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Add a marker in Sydney and move the camera
        LatLng bankPosition = new LatLng(latitude,longitude);
        googleMap.addMarker(new MarkerOptions().position(bankPosition).title(name))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maps_ic_location));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bankPosition, 15));

        googleMap.setOnMarkerClickListener(marker -> {

            if (bottomSheet.getState() == BottomSheetBehavior.STATE_HIDDEN)
                bottomSheet.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

            return true;
        });

        googleMap.setOnMapClickListener(latLng -> bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN));
    }

    /**
     * Method to set up BottomSheetFragment below Maps and callback functions to react to the user actions.
     */
    private void setUpBottomSheet() {

        bottomSheet = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheet.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

        bottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {

                switch (newState) {

                    case BottomSheetBehavior.STATE_EXPANDED:
                        showTopBar();
                        break;

                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
                        bottomSheet.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                        break;

                    case BottomSheetBehavior.STATE_DRAGGING:
                        hideTopBar();
                        break;

                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;

                    case BottomSheetBehavior.STATE_SETTLING:
                        break;

                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

    }

    /**
     * Method to show top bar inside BottomSheet.
     */
    private void showTopBar() {

        expendedTopBar.setVisibility(VISIBLE);
        collapsedTopBar.setVisibility(INVISIBLE);

    }

    /**
     * Method to hide top bar inside BottomSheet.
     */
    private void hideTopBar() {

        expendedTopBar.setVisibility(INVISIBLE);
        collapsedTopBar.setVisibility(VISIBLE);

    }

    private void moveToPayment () {

        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
        finish();

    }
}
