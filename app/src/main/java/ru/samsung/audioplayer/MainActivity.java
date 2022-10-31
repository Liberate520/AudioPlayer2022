package ru.samsung.audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    ImageView play, pause, rewind, forward;
    SeekBar seekBar;
    TextView maxLength, progress;
    MediaPlayer player;
    List<Track> trackList;
    int currentTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        rewind = findViewById(R.id.prev);
        forward = findViewById(R.id.forward);
        seekBar = findViewById(R.id.seekBar);
        maxLength = findViewById(R.id.time_sound);
        progress = findViewById(R.id.current_progress);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        rewind.setOnClickListener(this);
        forward.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);

        trackList = new ArrayList<>();
        trackList.add(new Track(R.raw.track1));
        trackList.add(new Track(R.raw.track2));
        trackList.add(new Track(R.raw.track3));

        createSound(0);
    }

    private void createSound(int trackNumber){
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
        }
        player = MediaPlayer.create(this, trackList.get(trackNumber).getId());
        String maxLengthSound = parseTimeSound(player.getDuration());
        maxLength.setText(maxLengthSound);

//        player.selectTrack(trackList.get(trackNumber).getId());
    }

    private void peremotka(int millisecond){
        if (player != null) {
            player.seekTo(player.getCurrentPosition() + millisecond);
        }
    }

    private void nextSound(int trackNumber){
        if (trackNumber >= trackList.size()){
            trackNumber = 0;
            currentTrack = trackNumber;
        }
        if (trackNumber < 0){
            trackNumber = trackList.size() - 1;
            currentTrack = trackNumber;
        }
        createSound(trackNumber);
    }

    private String parseTimeSound(int millis){
        int second = millis / 1000;
        int minute = second / 60;
        second = second - minute * 60;
        return String.format("%02d:%02d", minute, second);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play:
                player.start();
                play.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                break;
            case R.id.pause:
                player.pause();
                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
                break;
            case R.id.prev:
                nextSound(--currentTrack);
                player.start();
                break;
            case R.id.forward:
                nextSound(++currentTrack);
                player.start();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}