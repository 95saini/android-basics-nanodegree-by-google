package com.example.arieldiax.musicalstructureapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AlbumsActivity extends AppCompatActivity {

    /**
     * @var mMainActivityButton Activity button for Main.
     */
    private Button mMainActivityButton;

    /**
     * @var mPlaylistsActivityButton Activity button for Playlists.
     */
    private Button mPlaylistsActivityButton;

    /**
     * @var mSongsActivityButton Activity button for Songs.
     */
    private Button mSongsActivityButton;

    /**
     * @var mArtistsActivityButton Activity button for Artists.
     */
    private Button mArtistsActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        initUi();
        init();
    }

    /**
     * Initializes the user interface view bindings.
     */
    private void initUi() {
        mMainActivityButton = (Button) findViewById(R.id.main_activity_button);
        mPlaylistsActivityButton = (Button) findViewById(R.id.playlists_activity_button);
        mSongsActivityButton = (Button) findViewById(R.id.songs_activity_button);
        mArtistsActivityButton = (Button) findViewById(R.id.artists_activity_button);
    }

    /**
     * Initializes the back end logic bindings.
     */
    private void init() {
        mMainActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startCustomActivity(MainActivity.class);
            }
        });
        mPlaylistsActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startCustomActivity(PlaylistsActivity.class);
            }
        });
        mSongsActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startCustomActivity(SongsActivity.class);
            }
        });
        mArtistsActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startCustomActivity(ArtistsActivity.class);
            }
        });
    }

    /**
     * Starts the custom activity layout, and finishes the current one.
     */
    private void startCustomActivity(Class activityClass) {
        Intent activityIntent = new Intent(this, activityClass);
        startActivity(activityIntent);
        finish();
    }
}
