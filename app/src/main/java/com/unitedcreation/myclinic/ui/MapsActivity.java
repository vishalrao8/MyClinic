
package com.unitedcreation.myclinic.ui;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unitedcreation.myclinic.R;

import java.util.Objects;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private BottomSheetBehavior<ConstraintLayout> bottomSheet;

    @BindView(R.id.layout_maps_sheet)
    ConstraintLayout bottomSheetLayout;

    @BindView(R.id.iv_maps_down)
    ImageView downButton;

    @BindView(R.id.layout_maps_expended)
    ConstraintLayout expendedTopBar;

    @BindView(R.id.layout_maps_collapsed)
    ConstraintLayout collapsedTopBar;

    @BindView(R.id.fab_maps_pay)
    FloatingActionButton payNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_maps_map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        setUpBottomSheet();

        downButton.setOnClickListener(v -> {

            bottomSheet.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            hideTopBar();

        });
    }

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

    private void showTopBar() {

        expendedTopBar.setVisibility(VISIBLE);
        collapsedTopBar.setVisibility(INVISIBLE);

    }

    private void hideTopBar() {

        expendedTopBar.setVisibility(INVISIBLE);
        collapsedTopBar.setVisibility(VISIBLE);

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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title(getString(R.string.maps_bank_name)))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maps_ic_location));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMarkerClickListener(marker -> {

            if (bottomSheet.getState() == BottomSheetBehavior.STATE_HIDDEN)
            bottomSheet.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

            return true;
        });
    }
}
