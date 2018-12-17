package com.example.olleh.mpf;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;


public class Gallery extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_layout);

        getWindow().getDecorView().setSystemUiVisibility(

                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |

                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |

                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |

                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |

                        View.SYSTEM_UI_FLAG_FULLSCREEN |

                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        );


        final GalleryAdapter galleryAdapter = new GalleryAdapter(this);

        GridView gridView = (GridView) findViewById(R.id.GridView);
        gridView.setAdapter(galleryAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { // 각 이미지 클릭시 동작
                galleryAdapter.clickImageViewer(i);
            }
        });

       Button b = (Button)findViewById(R.id.bt_apply);
       b.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v){
               Switch last = (Switch)findViewById(R.id.timeAsending);
               Switch place = (Switch)findViewById(R.id.place);

               CheckBox ch_char = (CheckBox)findViewById(R.id.ch_char);
               CheckBox ch_docu = (CheckBox)findViewById(R.id.ch_docu);
               CheckBox ch_food = (CheckBox)findViewById(R.id.ch_food);
               CheckBox ch_othe = (CheckBox)findViewById(R.id.ch_othe);
               CheckBox ch_view = (CheckBox)findViewById(R.id.ch_view);
               String charator =(ch_char.isChecked())?"char":"xxxx";
               String documents =(ch_docu.isChecked())?"docu":"xxxx";
               String food=(ch_food.isChecked())?"food":"xxxx";
               String others=(ch_othe.isChecked())?"othe":"xxxx";
               String view=(ch_view.isChecked())?"view":"xxxx";
               Log.e("솔팅전","로그캣");
               galleryAdapter.setSortingTag(charator,documents,food,others,view);
               galleryAdapter.setSortingbyDate(last.isChecked());
               Log.e("시간별 정렬"," "+last.isChecked());
               //galleryAdapter.setSortingbyPlace();
               Log.e("뉴하기전","로그캣");
               GridView gridView = (GridView) findViewById(R.id.GridView);
               Log.e("뉴하고나서","로그캣");
               gridView.setAdapter(galleryAdapter);
               Log.e("셋","로그캣");


           }
       });
    }


    public class GalleryAdapter extends BaseAdapter {

        private String picturePath;
        private ArrayList<String> pictureList;
        private File[] pictureFileList;
        private Context mContext;
        public int COMPARETYPE_NAME = 0;
        public int COMPARETYPE_DATE = 1;
        private int diff=0;
//mk
        public void setSortingbyDate(Boolean bool){

            for(int i=0; i<pictureFileList.length;i++){
                pictureFileList=sortFileList(pictureFileList,COMPARETYPE_NAME);
            }

            if(bool==false){
                File[] temp=new File[diff];
                for(int i=0;i<diff;i++){
                    temp[i]=pictureFileList[diff-1-i];
                }
                for(int i =0; i<diff;i++) {
                    pictureFileList[i]=temp[i];
                }
                Log.e("거짓 제대로 들어옴","진짜");
            }
            Log.e("rnquf","구별");
          /*  for(int i=0; i<this.pictureFileList.length;i++){
                Log.e("원소 ",this.pictureFileList[i].getName());
            }*/
        }

        public File[] sortFileList(File[] files, final int compareType)
        {

            Arrays.sort(files,
                    new Comparator<Object>()
                    {
                        @Override
                        public int compare(Object object1, Object object2) {

                            String s1 = "";
                            String s2 = "";

                            if(compareType == COMPARETYPE_NAME){

                                    s1 = ((File) object1).getName();
                                    s2 = ((File) object2).getName();


                            }
                            else if(compareType == COMPARETYPE_DATE){

                                    s1 = ((File) object1).lastModified() + "";
                                    s2 = ((File) object2).lastModified() + "";

                            }


                            return s1.compareTo(s2);

                        }
                    });

            return files;
        }


        public void setSortingTag(String charator,String documents,String food,String others,String view){
            File[] pictureFileList = (new File(picturePath).listFiles());
            picturePath = Environment.getExternalStorageDirectory() + "/Capture";
            File[] files = new File(picturePath).listFiles();
            String compare;
            Vector<File> vector = new Vector<File>(0);
            int j=0;
            for(File file:files){

                compare=file.getName();
                //Log.e("아무거나",compare);
                //Log.e("아무거나",charator+documents+food+others+view);
                if(CompareString(compare,charator)||CompareString(compare,documents)||CompareString(compare,food)||CompareString(compare,others)||CompareString(compare,view)){
                    vector.add(file);
                }
                else{
                    j++;
                }
            }
            Log.e("태그솔팅 끝","!!");

            Log.e("벡터에 넣음","크기는 "+vector.size());
            this.diff=vector.size();
            File[] temp= new File[vector.size()];
            temp=(File[])vector.toArray(pictureFileList);
            for(int i=0;i<diff;i++){
            this.pictureFileList[i]=temp[i];
            }
            Log.e("템프 가리킴","!!");
            Log.e("길이는 ",": "+this.pictureFileList.length);
            for(int i=0; i<vector.size();i++){
                Log.e("원소 ",this.pictureFileList[i].getName());
            }
        }
        public boolean CompareString(String a, String b){
            int flag=0;
            for(int i=12;i<16;i++){
                if(a.charAt(i)==b.charAt(i-12)){
                    flag++;
                    Log.e("플래그", " : "+flag);
                }
            }
            return (flag==4)?true:false;
        }

        public void setPictureFileList(File[] pictureFileList) {
            this.pictureFileList = pictureFileList;
        }

        public GalleryAdapter(Context c) {
            picturePath = Environment.getExternalStorageDirectory() + "/Capture"; // 디렉토리 경로
            pictureList = new ArrayList<String>();
            pictureFileList = (new File(picturePath).listFiles()); // 디렉토리의 파일목록을 file배열로 변환
            mContext = c;

        }

        public final void clickImageViewer(int index) {
            Intent intent = new Intent(mContext, ClickPicture.class);
            String imagePath = pictureFileList[index].getAbsolutePath();
            Log.e("?!!!! 경로",imagePath);
            intent.putExtra("imagePath", imagePath);
            startActivity(intent);
        }

        @Override
        public int getCount() {
            return pictureFileList.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            ImageView imageView;

            if (view == null) {

                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(3, 3, 3, 3);
            } else {
                imageView = (ImageView) view;
            }

            final int pos = position;
            if(pos>=diff){return imageView;}
            BitmapFactory.Options bo = new BitmapFactory.Options();
            bo.inSampleSize = 32; // 이미지 크기 줄임
            Bitmap bmp = BitmapFactory.decodeFile(this.pictureFileList[pos].getPath(), bo);
            // position에 다른 파일의 절대경로와 옵션을 비트맵에 저장
            Bitmap resized = Bitmap.createScaledBitmap(bmp, 95, 95, true); // 크기 조절

            imageView.setImageBitmap(resized); // 이미지 뷰에 할당


            return imageView;
        }


    }
}

