package com.example.appmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtTitle, txtTimeSong, txtTimeTotal;
    SeekBar skSong;
    ImageButton btnPrev, btnPlay, btnStop, btnNext;
    ImageView img;

    ArrayList<Song> arraySong;
    int position = 0;

    MediaPlayer mediaPlayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        AddSong();

        animation = AnimationUtils.loadAnimation(this,R.anim.disk);

        MediaPlayer();

        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                 mediaPlayer.seekTo(skSong.getProgress());
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if (position > arraySong.size() - 1) {
                    position = 0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                MediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause100);
                SetTimeToatl();
                UpdateTimeSong();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position --;
                if (position < 0) {
                    position = arraySong.size() - 1;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                MediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause100);
                SetTimeToatl();
                UpdateTimeSong();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.play100);
                MediaPlayer();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    // neu dang chay thi pause -> doi hinh thay play
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play100);
                }else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause100);
                }
                SetTimeToatl();
                UpdateTimeSong();
                img.startAnimation(animation);
            }
        });
    }
    private void UpdateTimeSong() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinDangGio = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(dinDangGio.format(mediaPlayer.getCurrentPosition()));

                // update progess
                skSong.setProgress(mediaPlayer.getCurrentPosition());

                // Kiem tra thoi gian bai hat neu ket thuc

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position > arraySong.size() - 1) {
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        MediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause100);
                        SetTimeToatl();
                        UpdateTimeSong();
                    }
                });

                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void  SetTimeToatl() {
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(dinhDangGio.format(mediaPlayer.getDuration()));

        // Gan max skSong = mediaPlayer.getDuration

        skSong.setMax(mediaPlayer.getDuration());
    }

    private void MediaPlayer() {
        mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());
    }

    private void AddSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Payphone",R.raw.payphone));
        arraySong.add(new Song("Sugar", R.raw.sugar));
    }

    private void AnhXa() {
        txtTitle     = (TextView) findViewById(R.id.textViewName);
        txtTimeSong  = (TextView) findViewById(R.id.textViewTimeSong);
        txtTimeTotal = (TextView) findViewById(R.id.textViewTimeTotal);

        skSong       = (SeekBar) findViewById(R.id.seekBar);

        btnPrev      = (ImageButton) findViewById(R.id.imageButtonPrev);
        btnPlay      = (ImageButton) findViewById(R.id.imageButtonPlay);
        btnStop      = (ImageButton) findViewById(R.id.imageButtonStop);
        btnNext      = (ImageButton) findViewById(R.id.imageButtonNext);

        img          = (ImageView) findViewById(R.id.imageView);
    }
}
