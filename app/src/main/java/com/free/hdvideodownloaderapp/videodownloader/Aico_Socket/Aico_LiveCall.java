package com.free.hdvideodownloaderapp.videodownloader.Aico_Socket;

import android.Manifest;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;


import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.List;

public class Aico_LiveCall extends AppCompatActivity implements SurfaceHolder.Callback {


    AudioManager audioManager;
    ImageView actionBlock, actionDialog;
    private SurfaceView imgSurface;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Handler customHandler = new Handler();
    int flag = 0;
    private OrientationEventListener myOrientationEventListener;
    int iOrientation = 0;
    int mOrientation = 90;
    private int mPhotoAngle = 90;

    private int random_Data;


    Uri[] randomArray = {
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_85.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_19.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_20.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_46.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_47.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_48.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_49.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_50.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_51.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_52.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_60.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_61.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_62.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_63.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_53.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_54.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_55.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_56.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_57.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_58.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_59.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_64.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_65.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_66.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_67.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_68.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_16.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_25.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_26.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_27.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_28.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_29.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_30.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_31.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_32.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_33.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_34.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_35.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_36.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_37.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_38.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_39.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_40.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_41.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_42.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_43.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_44.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_45.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_69.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_70.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_71.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_72.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_73.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_74.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_75.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_76.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_77.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_78.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_79.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_80.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_81.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_1.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_2.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_3.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_4.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_5.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_6.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_7.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_8.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_9.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_10.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_11.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_12.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_13.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_14.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_15.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_17.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_18.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_21.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_22.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_23.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_24.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_82.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_83.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_84.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_87.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_88.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_89.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_90.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_91.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_92.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_93.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_94.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_95.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_96.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_97.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_98.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_99.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_100.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_101.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_102.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_103.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_104.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_105.mp4"),
            Uri.parse("https://s3.ap-south-1.amazonaws.com/fake.vc.videos/videos/video_106.mp4")
    };


    LinearLayout iv_call_cut;
    VideoView videoView;
    TextView tv_connecting;
    ImageView iv_swich_camera, iv_video_on, iv_voice_on, iv_video_off, iv_voice_off, vOff;
    private ConstraintLayout mainCL;


    @Override
    protected void onPause() {
        super.onPause();

        try {

            if (customHandler != null)
                customHandler.removeCallbacksAndMessages(null);

            if (myOrientationEventListener != null)
                myOrientationEventListener.enable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {


        super.onResume();
        try {
            if (myOrientationEventListener != null)
                myOrientationEventListener.enable();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aico_vc_live);

        audioManager = (AudioManager) getApplicationContext().getSystemService(AUDIO_SERVICE);

        imgSurface = findViewById(R.id.imgSurface);
        actionBlock = findViewById(R.id.actionBlock);
        actionDialog = findViewById(R.id.actionDialog);
        videoView = findViewById(R.id.videoview);
        iv_call_cut = findViewById(R.id.iv_call_cut);
        iv_swich_camera = findViewById(R.id.iv_swich_camera);
        iv_video_on = findViewById(R.id.iv_video_on);
        iv_voice_on = findViewById(R.id.iv_voice_on);
        iv_voice_off = findViewById(R.id.iv_voice_off);
        iv_video_off = findViewById(R.id.iv_video_off);
        tv_connecting = findViewById(R.id.tv_connecting);
        vOff = findViewById(R.id.vOff);
        mainCL = findViewById(R.id.mainCL);

        //  isAppPermissionGranted();

        if (isAppPermissionGranted()) {
            // Permission Granted...
            captureImageCallback();
            if (camera != null) {
                Camera.CameraInfo info = new Camera.CameraInfo();
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {

                }
            }

        } else {
            isAppPermissionGranted();

        }


        //code here...
        iv_video_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_video_on.setVisibility(View.INVISIBLE);
                iv_video_off.setVisibility(View.VISIBLE);

                imgSurface.setVisibility(View.INVISIBLE);
                vOff.setVisibility(View.VISIBLE);
            }
        });

        iv_video_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_video_off.setVisibility(View.INVISIBLE);
                iv_video_on.setVisibility(View.VISIBLE);

                imgSurface.setVisibility(View.VISIBLE);
                vOff.setVisibility(View.INVISIBLE);
            }
        });


        iv_voice_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //voice off...
                audioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

                iv_voice_on.setVisibility(View.INVISIBLE);
                iv_voice_off.setVisibility(View.VISIBLE);
            }
        });

        iv_voice_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //voice on...
                audioManager.adjustVolume(AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_PLAY_SOUND);

                iv_voice_off.setVisibility(View.INVISIBLE);
                iv_voice_on.setVisibility(View.VISIBLE);
            }
        });

        iv_call_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        actionBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Aico_LiveCall.this);
                final View view = getLayoutInflater().inflate(R.layout.aico_dlg_block, null);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                RelativeLayout no = view.findViewById(R.id.no);
                RelativeLayout yes = view.findViewById(R.id.yes);

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Aico_LiveCall.this, "User blocked successful!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        onBackPressed();
                    }
                });
            }
        });

        actionDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Aico_LiveCall.this);

                final View view = getLayoutInflater().inflate(R.layout.aico_dlg_report, null);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                TextView txt_dismiss, txt1, txt2, txt3, txt4, txt5, txt6;
                txt_dismiss = view.findViewById(R.id.txt_dismiss);
                txt1 = view.findViewById(R.id.txt_1);
                txt2 = view.findViewById(R.id.txt_2);
                txt3 = view.findViewById(R.id.txt_3);
                txt4 = view.findViewById(R.id.txt_4);
                txt5 = view.findViewById(R.id.txt_5);
                txt6 = view.findViewById(R.id.txt_6);

                txt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionReport(dialog);
                    }
                });

                txt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionReport(dialog);
                    }
                });

                txt3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionReport(dialog);
                    }
                });

                txt4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionReport(dialog);
                    }
                });

                txt5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionReport(dialog);
                    }
                });

                txt6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actionReport(dialog);
                    }
                });

                txt_dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_swich_camera.callOnClick();
            }
        }, 500);


        iv_swich_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    camera.stopPreview();
                    camera.release();

                    if (flag == 0) {
                        flag = 1;
                    } else {
                        flag = 0;
                    }
                    surfaceCreated(surfaceHolder);

                    imgSurface.setVisibility(View.INVISIBLE);
                    vOff.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgSurface.setVisibility(View.VISIBLE);
                            vOff.setVisibility(View.INVISIBLE);
                        }
                    }, 1000);
                } catch (Exception e) {
                    Toast.makeText(Aico_LiveCall.this, "Please don't click many times!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        randomCalling();


    }

    private void actionReport(Dialog dialog) {
        Toast.makeText(Aico_LiveCall.this, "Report submitted.", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        onBackPressed();
    }

    public boolean isAppPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //Permission is granted
                return true;
            } else {
                //Permission is revoked
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
                return false;
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            //Permission is granted
            Toast.makeText(this, "camera permission required!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    isAppPermissionGranted();
                    Toast.makeText(this, "camera permission required!", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void randomCalling() {
        SharedPreferences sh = getSharedPreferences("RandomSF", MODE_APPEND);
        random_Data = sh.getInt("KEY_RAND", 0);

        if (random_Data == 0) {

            SharedPreferences random_sf = getSharedPreferences("RandomSF", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = random_sf.edit();
            myEdit.putInt("KEY_RAND", Integer.parseInt(String.valueOf(random_Data)));
            myEdit.apply();

            videoView.setVideoURI(randomArray[0]);
        } else if (random_Data == 1) {
            videoView.setVideoURI(randomArray[1]);
        } else if (random_Data == 2) {
            videoView.setVideoURI(randomArray[2]);
        } else if (random_Data == 3) {
            videoView.setVideoURI(randomArray[3]);
        } else if (random_Data == 4) {
            videoView.setVideoURI(randomArray[4]);
        } else if (random_Data == 5) {
            videoView.setVideoURI(randomArray[5]);
        } else if (random_Data == 6) {
            videoView.setVideoURI(randomArray[6]);
        } else if (random_Data == 7) {
            videoView.setVideoURI(randomArray[7]);
        } else if (random_Data == 8) {
            videoView.setVideoURI(randomArray[8]);
        } else if (random_Data == 9) {
            videoView.setVideoURI(randomArray[9]);
        } else if (random_Data == 10) {
            videoView.setVideoURI(randomArray[10]);
        } else if (random_Data == 11) {
            videoView.setVideoURI(randomArray[11]);
        } else if (random_Data == 12) {
            videoView.setVideoURI(randomArray[12]);
        } else if (random_Data == 13) {
            videoView.setVideoURI(randomArray[13]);
        } else if (random_Data == 14) {
            videoView.setVideoURI(randomArray[14]);
        } else if (random_Data == 15) {
            videoView.setVideoURI(randomArray[15]);
        } else if (random_Data == 16) {
            videoView.setVideoURI(randomArray[16]);
        } else if (random_Data == 17) {
            videoView.setVideoURI(randomArray[17]);
        } else if (random_Data == 18) {
            videoView.setVideoURI(randomArray[18]);
        } else if (random_Data == 19) {
            videoView.setVideoURI(randomArray[19]);
        } else if (random_Data == 20) {
            videoView.setVideoURI(randomArray[20]);
        } else if (random_Data == 21) {
            videoView.setVideoURI(randomArray[21]);
        } else if (random_Data == 22) {
            videoView.setVideoURI(randomArray[22]);
        } else if (random_Data == 23) {
            videoView.setVideoURI(randomArray[23]);
        } else if (random_Data == 24) {
            videoView.setVideoURI(randomArray[24]);
        } else if (random_Data == 25) {
            videoView.setVideoURI(randomArray[25]);
        } else if (random_Data == 26) {
            videoView.setVideoURI(randomArray[26]);
        } else if (random_Data == 27) {
            videoView.setVideoURI(randomArray[27]);
        } else if (random_Data == 28) {
            videoView.setVideoURI(randomArray[28]);
        } else if (random_Data == 29) {
            videoView.setVideoURI(randomArray[29]);
        } else if (random_Data == 30) {
            videoView.setVideoURI(randomArray[30]);
        } else if (random_Data == 31) {
            videoView.setVideoURI(randomArray[31]);
        } else if (random_Data == 32) {
            videoView.setVideoURI(randomArray[32]);
        } else if (random_Data == 33) {
            videoView.setVideoURI(randomArray[33]);
        } else if (random_Data == 34) {
            videoView.setVideoURI(randomArray[34]);
        } else if (random_Data == 35) {
            videoView.setVideoURI(randomArray[35]);
        } else if (random_Data == 36) {
            videoView.setVideoURI(randomArray[36]);
        } else if (random_Data == 37) {
            videoView.setVideoURI(randomArray[37]);
        } else if (random_Data == 38) {
            videoView.setVideoURI(randomArray[38]);
        } else if (random_Data == 39) {
            videoView.setVideoURI(randomArray[39]);
        } else if (random_Data == 40) {
            videoView.setVideoURI(randomArray[40]);
        } else if (random_Data == 41) {
            videoView.setVideoURI(randomArray[41]);
        } else if (random_Data == 42) {
            videoView.setVideoURI(randomArray[42]);
        } else if (random_Data == 43) {
            videoView.setVideoURI(randomArray[43]);
        } else if (random_Data == 44) {
            videoView.setVideoURI(randomArray[44]);
        } else if (random_Data == 45) {
            videoView.setVideoURI(randomArray[45]);
        } else if (random_Data == 46) {
            videoView.setVideoURI(randomArray[46]);
        } else if (random_Data == 47) {
            videoView.setVideoURI(randomArray[47]);
        } else if (random_Data == 48) {
            videoView.setVideoURI(randomArray[48]);
        } else if (random_Data == 49) {
            videoView.setVideoURI(randomArray[49]);
        } else if (random_Data == 50) {
            videoView.setVideoURI(randomArray[50]);
        } else if (random_Data == 51) {
            videoView.setVideoURI(randomArray[51]);
        } else if (random_Data == 52) {
            videoView.setVideoURI(randomArray[52]);
        } else if (random_Data == 53) {
            videoView.setVideoURI(randomArray[53]);
        } else if (random_Data == 54) {
            videoView.setVideoURI(randomArray[54]);
        } else if (random_Data == 55) {
            videoView.setVideoURI(randomArray[55]);
        } else if (random_Data == 56) {
            videoView.setVideoURI(randomArray[56]);
        } else if (random_Data == 57) {
            videoView.setVideoURI(randomArray[57]);
        } else if (random_Data == 58) {
            videoView.setVideoURI(randomArray[58]);
        } else if (random_Data == 59) {
            videoView.setVideoURI(randomArray[59]);
        } else if (random_Data == 60) {
            videoView.setVideoURI(randomArray[60]);
        } else if (random_Data == 61) {
            videoView.setVideoURI(randomArray[61]);
        } else if (random_Data == 62) {
            videoView.setVideoURI(randomArray[62]);
        } else if (random_Data == 63) {
            videoView.setVideoURI(randomArray[63]);
        } else if (random_Data == 64) {
            videoView.setVideoURI(randomArray[64]);
        } else if (random_Data == 65) {
            videoView.setVideoURI(randomArray[65]);
        } else if (random_Data == 66) {
            videoView.setVideoURI(randomArray[66]);
        } else if (random_Data == 67) {
            videoView.setVideoURI(randomArray[67]);
        } else if (random_Data == 68) {
            videoView.setVideoURI(randomArray[68]);
        } else if (random_Data == 69) {
            videoView.setVideoURI(randomArray[69]);
        } else if (random_Data == 70) {
            videoView.setVideoURI(randomArray[70]);
        } else if (random_Data == 71) {
            videoView.setVideoURI(randomArray[71]);
        } else if (random_Data == 72) {
            videoView.setVideoURI(randomArray[72]);
        } else if (random_Data == 73) {
            videoView.setVideoURI(randomArray[73]);
        } else if (random_Data == 74) {
            videoView.setVideoURI(randomArray[74]);
        } else if (random_Data == 75) {
            videoView.setVideoURI(randomArray[75]);
        } else if (random_Data == 76) {
            videoView.setVideoURI(randomArray[76]);
        } else if (random_Data == 77) {
            videoView.setVideoURI(randomArray[77]);
        } else if (random_Data == 78) {
            videoView.setVideoURI(randomArray[78]);
        } else if (random_Data == 79) {
            videoView.setVideoURI(randomArray[79]);
        } else if (random_Data == 80) {
            videoView.setVideoURI(randomArray[80]);
        } else if (random_Data == 81) {
            videoView.setVideoURI(randomArray[81]);
        } else if (random_Data == 82) {
            videoView.setVideoURI(randomArray[82]);
        } else if (random_Data == 83) {
            videoView.setVideoURI(randomArray[83]);
        } else if (random_Data == 84) {
            videoView.setVideoURI(randomArray[84]);
        } else if (random_Data == 85) {
            videoView.setVideoURI(randomArray[85]);
        } else if (random_Data == 86) {
            videoView.setVideoURI(randomArray[86]);
        } else if (random_Data == 87) {
            videoView.setVideoURI(randomArray[87]);
        } else if (random_Data == 88) {
            videoView.setVideoURI(randomArray[88]);
        } else if (random_Data == 89) {
            videoView.setVideoURI(randomArray[89]);
        } else if (random_Data == 90) {
            videoView.setVideoURI(randomArray[90]);
        } else if (random_Data == 91) {
            videoView.setVideoURI(randomArray[91]);
        } else if (random_Data == 92) {
            videoView.setVideoURI(randomArray[92]);
        } else if (random_Data == 93) {
            videoView.setVideoURI(randomArray[93]);
        } else if (random_Data == 94) {
            videoView.setVideoURI(randomArray[94]);
        } else if (random_Data == 95) {
            videoView.setVideoURI(randomArray[95]);
        } else if (random_Data == 96) {
            videoView.setVideoURI(randomArray[96]);
        } else if (random_Data == 97) {
            videoView.setVideoURI(randomArray[97]);
        } else if (random_Data == 98) {
            videoView.setVideoURI(randomArray[98]);
        } else if (random_Data == 99) {
            videoView.setVideoURI(randomArray[99]);
        } else if (random_Data == 100) {
            videoView.setVideoURI(randomArray[100]);
        } else if (random_Data == 101) {
            videoView.setVideoURI(randomArray[101]);
        } else if (random_Data == 102) {
            videoView.setVideoURI(randomArray[102]);
        } else if (random_Data == 103) {
            videoView.setVideoURI(randomArray[103]);
        } else if (random_Data == 104) {
            videoView.setVideoURI(randomArray[104]);
        } else if (random_Data == 105) {
            videoView.setVideoURI(randomArray[105]);
        }  else {
            SharedPreferences obj2 = getSharedPreferences("RandomSF", MODE_APPEND);
            obj2.edit().putInt("KEY_RAND", random_Data = 1).apply();
        }


        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //mp.stop();
                tv_connecting.setVisibility(View.INVISIBLE);
            }
        });

        videoView.requestFocus();
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onBackPressed();
            }
        });


    }


    private void captureImageCallback() {

        surfaceHolder = imgSurface.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    public void refreshCamera() {

        if (surfaceHolder.getSurface() == null) {
            return;
        }
        try {
            camera.stopPreview();
            Camera.Parameters param = camera.getParameters();

            if (flag == 0) {
            }


            refrechCameraPriview(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refrechCameraPriview(Camera.Parameters param) {
        try {
            camera.setParameters(param);
            setCameraDisplayOrientation(0);

            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCameraDisplayOrientation(int cameraId) {

        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);

        int rotation = getWindowManager().getDefaultDisplay().getRotation();

        if (Build.MODEL.equalsIgnoreCase("Nexus 6") && flag == 1) {
            rotation = Surface.ROTATION_180;
        }
        int degrees = 0;
        switch (rotation) {

            case Surface.ROTATION_0:

                degrees = 0;
                break;

            case Surface.ROTATION_90:

                degrees = 90;
                break;

            case Surface.ROTATION_180:

                degrees = 180;
                break;

            case Surface.ROTATION_270:

                degrees = 270;
                break;

        }

        int result;

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {

            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror

        } else {
            result = (info.orientation - degrees + 360) % 360;

        }

        camera.setDisplayOrientation(result);

    }


    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        try {
            if (flag == 0) {
                camera = Camera.open(0);
            } else {
                camera = Camera.open(1);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return;
        }

        try {
            Camera.Parameters param;
            param = camera.getParameters();
            List<Camera.Size> sizes = param.getSupportedPreviewSizes();
            //get diff to get perfact preview sizes
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;
            long diff = (height * 1000 / width);
            long cdistance = Integer.MAX_VALUE;
            int idx = 0;
            for (int i = 0; i < sizes.size(); i++) {
                long value = (long) (sizes.get(i).width * 1000) / sizes.get(i).height;
                if (value > diff && value < cdistance) {
                    idx = i;
                    cdistance = value;
                }
//                //Log.e("WHHATSAPP", "width=" + sizes.get(i).width + " height=" + sizes.get(i).height);
            }
//            //Log.e("WHHATSAPP", "INDEX:  " + idx);
            Camera.Size cs = sizes.get(idx);
            param.setPreviewSize(cs.width, cs.height);
            param.setPictureSize(cs.width, cs.height);
            camera.setParameters(param);
            setCameraDisplayOrientation(0);

            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        try {
            camera.stopPreview();
            camera.release();
            camera = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        refreshCamera();
    }


    @Override
    public void onBackPressed() {

        //add value...
        SharedPreferences obj = getSharedPreferences("RandomSF", MODE_APPEND);
        obj.edit().putInt("KEY_RAND", random_Data + 1).apply();

        //random_sf.edit().putInt("KEY_RAND", random_Data + 1).apply();
        //random_Data++;

//        //Log.e("dev", "random_Data - onBackPress >>>> " + random_Data);
        super.onBackPressed();
    }


}