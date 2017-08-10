package com.le.mychat.module.chat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.le.mychat.R;
import com.le.mychat.context.ShowPrompt;
import com.le.mychat.core.BaseFragment;
import com.le.mychat.core.FragmentInfo;
import com.le.mychat.core.FragmentInfoActivity;
import com.le.mychat.module.call.CallFragment;
import com.le.mychat.module.chat.enity.MessageInfo;
import com.le.mychat.module.chat.util.Constants;
import com.le.mychat.module.chat.util.PictureUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;


/**
 * 作者：Rance on 2016/12/13 16:01
 * 邮箱：rance935@163.com
 */
public class ChatFunctionFragment extends BaseFragment {
    private View rootView;
    private static final int CROP_PHOTO = 2;
    private static final int REQUEST_CODE_PICK_IMAGE = 3;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    private File output;
    private Uri imageUri;
    private String mPublicPhotoPath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_chat_function, container, false);
            ViewUtils.inject(this, rootView);
        }
        return rootView;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void processClick(View v) {

    }

    @OnClick({R.id.chat_function_photo, R.id.chat_function_photograph,R.id.chat_function_call})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chat_function_call:
                Bundle bundle=new Bundle();
                FragmentInfo info=new FragmentInfo(CallFragment.class,bundle);
                FragmentInfoActivity.startActivity(getContext(),info);
                break;
            case R.id.chat_function_photograph:
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);

                } else {
                    startTake();
                }
                break;
            case R.id.chat_function_photo:
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);

                } else {
                    choosePhoto();
                }
                break;
        }
    }
    private void startTake() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断是否有相机应用
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            //创建临时图片文件
            File photoFile = null;
            try {
                photoFile = PictureUtils.createPublicImageFile();
                mPublicPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //设置Action为拍照
            if (photoFile != null) {
                takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                //这里加入flag
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.le.chat.fileProvider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CROP_PHOTO);
            }
        }
    }



    /**
     * 拍照
     */
    private void takePhoto() {


//        /**
//         * 最后一个参数是文件夹的名称，可以随便起
//         */
//        File file = new File(Environment.getExternalStorageDirectory(), "拍照");
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        /**
//         * 这里将时间作为不同照片的名称
//         */
//        output = new File(file, System.currentTimeMillis() + ".jpg");
//
//        /**
//         * 如果该文件夹已经存在，则删除它，否则创建一个
//         */
//        try {
//            if (output.exists()) {
//                output.delete();
//            }
//            output.createNewFile();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        /**
//         * 隐式打开拍照的Activity，并且传入CROP_PHOTO常量作为拍照结束后回调的标志
//         */
//        imageUri = Uri.fromFile(output);
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, CROP_PHOTO);

    }


    /**
     * 从相册选取图片
     */
    private void choosePhoto() {
        /**
         * 打开选择图片的界面
         */
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            case CROP_PHOTO:
                if (res == Activity.RESULT_OK) {
                    try {
                        MessageInfo messageInfo = new MessageInfo();
                        messageInfo.setImageUrl(mPublicPhotoPath);
                        EventBus.getDefault().post(messageInfo);
                    } catch (Exception e) {
                    }
                } else {
                    Log.d(Constants.TAG, "失败");
                }

                break;
            case REQUEST_CODE_PICK_IMAGE:
                if (res == Activity.RESULT_OK) {
                        int sdkVersion = Integer.valueOf(Build.VERSION.SDK);
                        Uri uri = data.getData();
                        String url;
                        if (sdkVersion >= 19) {
                            url = PictureUtils.getPath_above19(getContext(), uri);
                        } else {
                            url = PictureUtils.getFilePath_below19(getContext(), uri);
                        }
                        MessageInfo messageInfo = new MessageInfo();
                        messageInfo.setImageUrl(url);
                        EventBus.getDefault().post(messageInfo);

                } else {
                    Log.d(Constants.TAG, "失败");
                }

                break;

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                ShowPrompt.showShort(getContext(),"请同意系统权限后继续");
            }
        }

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto();
            } else {
                ShowPrompt.showShort(getContext(),"请同意系统权限后继续");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}
