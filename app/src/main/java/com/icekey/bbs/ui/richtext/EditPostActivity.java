package com.icekey.bbs.ui.richtext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.icekey.bbs.R;
import com.icekey.bbs.adapter.BottomSheetRecyclerAdapter;
import com.icekey.bbs.bean.RecyclerUserData;
import com.ns.yc.yccustomtextlib.edit.inter.ImageLoader;
import com.ns.yc.yccustomtextlib.edit.inter.OnHyperEditListener;
import com.ns.yc.yccustomtextlib.edit.manager.HyperManager;
import com.ns.yc.yccustomtextlib.edit.view.HyperImageView;
import com.ns.yc.yccustomtextlib.edit.view.HyperTextEditor;
import com.ns.yc.yccustomtextlib.utils.HyperLibUtils;
import com.ns.yc.yccustomtextlib.utils.HyperLogUtils;
import com.pedaily.yc.ycdialoglib.fragment.CustomDialogFragment;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditPostActivity extends AppCompatActivity {
    private List<RecyclerUserData> list;
    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetBehavior mDialogBehavior;
    private Disposable subsInsert;
    private Disposable mDisposable;
    private static final int REQUEST_CODE_CHOOSE = 520;
    private int screenWidth;
    private int screenHeight;
    private ImageView image_back;
    private TextView text_postAdd;
    private EditText post_title;
    private ImageView image_bold;
    private ImageView image_image;
    private ImageView image_underLine;
    private ImageView image_italic;//??????
    private ImageView image_aite;
    private HyperTextEditor hte_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.add_post);
        initView();
        initListener();
        initHyper();
        screenWidth = CommonUtil.getScreenWidth(this);
        screenHeight = CommonUtil.getScreenHeight(this);
    }

    private void initView() {
        image_back = findViewById(R.id.back);
        text_postAdd = findViewById(R.id.post);
        post_title = findViewById(R.id.post_title);
        hte_content = findViewById(R.id.richTextEditor);
        image_bold = findViewById(R.id.bold);
        image_image = findViewById(R.id.image);
        image_underLine = findViewById(R.id.underLine);
        image_italic = findViewById(R.id.italic);
        image_aite = findViewById(R.id.aite);
    }

    private void initListener() {
        image_bold.setOnClickListener((v) -> hte_content.bold());
        image_image.setOnClickListener((v) -> {
            HyperLibUtils.hideSoftInput(this);
            PermissionUtils.checkWritePermissionsRequest(this, new PermissionCallBack() {
                @Override
                public void onPermissionGranted(Context context) {
                    callGallery();
                }

                @Override
                public void onPermissionDenied(Context context, int type) {
                    Toasty.error(context, "???????????????").show();
                }
            });
        });
        image_underLine.setOnClickListener((v) -> hte_content.underline());
        image_italic.setOnClickListener((v) -> hte_content.italic());
        image_back.setOnClickListener((v) -> finish());

        RecyclerUserData recyclerUserData = new RecyclerUserData("http://139.155.90.20/bbs/ico.png", "User", false);
        list = new ArrayList<>();
        list.add(recyclerUserData);
        list.add(recyclerUserData);
        list.add(recyclerUserData);
        list.add(recyclerUserData);
        list.add(recyclerUserData);

        image_aite.setOnClickListener((v) -> bottomSheet());


        hte_content.setOnHyperListener(new OnHyperEditListener() {
            @Override
            public void onImageClick(View view, String imagePath) {
                //??????????????????

            }

            @Override
            public void onRtImageDelete(String imagePath) {
                //????????????????????????
            }

            @Override
            public void onImageCloseClick(final View view) {
                //??????????????????????????????
                CustomDialogFragment
                        .create((EditPostActivity.this).getSupportFragmentManager())
                        .setTitle("????????????")
                        .setContent("????????????????????????????")
                        .setCancelContent("??????")
                        .setOkContent("??????")
                        .setDimAmount(0.5f)
                        .setOkColor(EditPostActivity.this.getResources().getColor(R.color.color_000000))
                        .setCancelOutside(true)
                        .setCancelListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CustomDialogFragment.dismissDialogFragment();
                            }
                        })
                        .setOkListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CustomDialogFragment.dismissDialogFragment();
                                hte_content.onImageCloseClick(view);
                            }
                        })
                        .show();
            }
        });
    }

    private void bottomSheet() {
        if (bottomSheetDialog == null) {
            //????????????
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_bottomsheet, null, false);
            RecyclerView recyclerView = view.findViewById(R.id.bottomSheetRecycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            BottomSheetRecyclerAdapter mainAdapter = new BottomSheetRecyclerAdapter(list);
            recyclerView.setAdapter(mainAdapter);
            bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
            //????????????dialog???????????????
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            //???????????? ?????????????????????????????????
            bottomSheetDialog.getWindow().setDimAmount(0f);
            //????????????
            bottomSheetDialog.setContentView(view);
            //????????????

            mDialogBehavior = BottomSheetBehavior.from(view.getRootView());
            //dialog?????????
            mDialogBehavior.setPeekHeight(getWindowHeight());
        }
        //??????
        bottomSheetDialog.show();
        //???????????????????????????
        mDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                //??????BottomSheet???????????????
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetDialog.dismiss();
                    mDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                //?????????????????????????????????slideOffset?????????????????????
            }
        });

    }

    private int getWindowHeight() {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int heightPixels = point.y;

        int reHeight = heightPixels - heightPixels / 4;
        Log.d("Height", reHeight + "");
        //????????????????????????????????????3/4
        return reHeight;
    }

    private void callGallery() {
        Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))//????????????????????????MimeType.allOf()
                .countable(true)//true:?????????????????????;false:?????????????????????
                .maxSelectable(3)//?????????????????????9
                //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(this.getResources().getDimensionPixelSize(R.dimen.photo))//???????????????????????????
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//??????????????????????????????????????????
                .thumbnailScale(0.85f)//????????????
                .theme(R.style.Matisse_Zhihu)//??????  ???????????? R.style.Matisse_Dracula
                .imageEngine(new MyGlideEngine())//?????????????????????Glide4?????????????????????
                .capture(true) //?????????????????????????????????7.0???????????????????????????
                //??????1 true????????????????????????????????????false????????????????????????????????????2??? AndroidManifest???authorities????????????????????????7.0?????? ????????????
                .captureStrategy(new CaptureStrategy(true, "com.sendtion.matisse.fileprovider"))//???????????????
                .forResult(REQUEST_CODE_CHOOSE);//?????????
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //????????????????????????
        boolean softInputVisible = HyperLibUtils.isSoftInputVisible(this);
        if (softInputVisible) {
            HyperLibUtils.hideSoftInput(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == REQUEST_CODE_CHOOSE) {
                    //????????????????????????
                    insertImagesSync(data);
                }
            }
        }
    }

    private void insertImagesSync(final Intent data) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                try {
                    hte_content.measure(0, 0);
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    // ??????????????????????????????
                    for (Uri imageUri : mSelected) {
                        String imagePath = HyperLibUtils.getFilePathFromUri(EditPostActivity.this, imageUri);

                        Bitmap bitmap = HyperLibUtils.getSmallBitmap(imagePath, screenWidth, screenHeight);
                        //????????????
                        imagePath = SDCardUtil.saveToSdCard(bitmap);
                        //Log.e(TAG, "###imagePath="+imagePath);
                        emitter.onNext(imagePath);
                    }

                    // ???????????????????????? http://pics.sc.chinaz.com/files/pic/pic9/201904/zzpic17414.jpg
                    //emitter.onNext("http://pics.sc.chinaz.com/files/pic/pic9/201903/zzpic16838.jpg");
                    //emitter.onNext("http://b.zol-img.com.cn/sjbizhi/images/10/640x1136/1572123845476.jpg");
                    //emitter.onNext("https://img.ivsky.com/img/tupian/pre/201903/24/richu_riluo-013.jpg");
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        })
                //.onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//???????????????io
                .observeOn(AndroidSchedulers.mainThread())//???????????????UI??????
                .subscribe(new Observer<String>() {
                    @Override
                    public void onComplete() {
                        Toasty.success(EditPostActivity.this, "??????????????????").show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toasty.error(EditPostActivity.this, "??????????????????:" + e.getMessage()).show();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        subsInsert = d;
                    }

                    @Override
                    public void onNext(String imagePath) {
                        hte_content.insertImage(imagePath);
                    }
                });
    }

    private void initHyper() {
        HyperManager.getInstance().setImageLoader(new ImageLoader() {
            @Override
            public void loadImage(final String imagePath, final HyperImageView imageView, final int imageHeight) {
                Log.e("---", "imageHeight: " + imageHeight);
                //?????????????????????
                if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
                    Glide.with(getApplicationContext()).asBitmap()
                            .load(imagePath)
                            .dontAnimate()
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    if (imageHeight > 0) {//????????????
                                        if (imageView.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                                                    FrameLayout.LayoutParams.MATCH_PARENT, imageHeight);//?????????????????????????????????????????????
                                            lp.bottomMargin = 10;//??????????????????
                                            imageView.setLayoutParams(lp);
                                        } else if (imageView.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                                            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                                                    FrameLayout.LayoutParams.MATCH_PARENT, imageHeight);//?????????????????????????????????????????????
                                            lp.bottomMargin = 10;//??????????????????
                                            imageView.setLayoutParams(lp);
                                        }
                                        Glide.with(getApplicationContext()).asBitmap().load(imagePath).centerCrop()
                                                .placeholder(R.drawable.img_load_fail).error(R.drawable.img_load_fail).into(imageView);
                                    } else {//???????????????
                                        Glide.with(getApplicationContext()).asBitmap().load(imagePath)
                                                .placeholder(R.drawable.img_load_fail).error(R.drawable.img_load_fail).into(new TransformationScale(imageView));
                                    }
                                }
                            });
                } else { //?????????????????????
                    if (imageHeight > 0) {//????????????
                        if (imageView.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                                    FrameLayout.LayoutParams.MATCH_PARENT, imageHeight);//?????????????????????????????????????????????
                            lp.bottomMargin = 10;//??????????????????
                            imageView.setLayoutParams(lp);
                        } else if (imageView.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                                    FrameLayout.LayoutParams.MATCH_PARENT, imageHeight);//?????????????????????????????????????????????
                            lp.bottomMargin = 10;//??????????????????
                            imageView.setLayoutParams(lp);
                        }

                        Glide.with(getApplicationContext()).asBitmap().load(imagePath).centerCrop()
                                .placeholder(R.drawable.img_load_fail).error(R.drawable.img_load_fail).into(imageView);
                    } else {//???????????????
                        Glide.with(getApplicationContext()).asBitmap().load(imagePath)
                                .placeholder(R.drawable.img_load_fail).error(R.drawable.img_load_fail).into(new TransformationScale(imageView));
                    }
                    if (imageHeight > 0) {
                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(imagePath)
                                .centerCrop()
                                .placeholder(R.drawable.img_load_fail)
                                .error(R.drawable.img_load_fail)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        int width = resource.getWidth();
                                        int height = resource.getHeight();
                                        HyperLogUtils.d("????????????--3--" + height + "----" + width);
                                        imageView.setImageBitmap(resource);
                                        ViewParent parent = imageView.getParent();
                                        int imageHeight = 0;
                                        //??????????????????200???????????????100???????????????????????????????????????????????????????????????????????????
                                        if (height > HyperLibUtils.dip2px(EditPostActivity.this, 200)) {
                                            if (parent instanceof RelativeLayout) {
                                                ViewGroup.LayoutParams layoutParams = ((RelativeLayout) parent).getLayoutParams();
                                                layoutParams.height = HyperLibUtils.dip2px(EditPostActivity.this, 200);
                                                ((RelativeLayout) parent).setLayoutParams(layoutParams);
                                            } else if (parent instanceof FrameLayout) {
                                                ViewGroup.LayoutParams layoutParams = ((FrameLayout) parent).getLayoutParams();
                                                layoutParams.height = HyperLibUtils.dip2px(EditPostActivity.this, 200);
                                                ((FrameLayout) parent).setLayoutParams(layoutParams);
                                                HyperLogUtils.d("????????????--4--");
                                            }
                                            imageHeight = HyperLibUtils.dip2px(EditPostActivity.this, 200);
                                        } else if (height > HyperLibUtils.dip2px(EditPostActivity.this, 100)
                                                && height < HyperLibUtils.dip2px(EditPostActivity.this, 200)) {
                                            //???????????????
                                            HyperLogUtils.d("????????????--5--");
                                            imageHeight = height;
                                        } else {
                                            if (parent instanceof RelativeLayout) {
                                                ViewGroup.LayoutParams layoutParams = ((RelativeLayout) parent).getLayoutParams();
                                                layoutParams.height = HyperLibUtils.dip2px(EditPostActivity.this, 100);
                                                ((RelativeLayout) parent).setLayoutParams(layoutParams);
                                            } else if (parent instanceof FrameLayout) {
                                                ViewGroup.LayoutParams layoutParams = ((FrameLayout) parent).getLayoutParams();
                                                layoutParams.height = HyperLibUtils.dip2px(EditPostActivity.this, 100);
                                                ((FrameLayout) parent).setLayoutParams(layoutParams);
                                                HyperLogUtils.d("????????????--6--");
                                            }
                                            imageHeight = HyperLibUtils.dip2px(EditPostActivity.this, 100);
                                        }
                                        //????????????????????????????????????????????????????????????????????????
                                        if (imageView.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                                                    FrameLayout.LayoutParams.MATCH_PARENT, imageHeight);//?????????????????????????????????????????????
                                            lp.bottomMargin = 10;//??????????????????
                                            imageView.setLayoutParams(lp);
                                        } else if (imageView.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                                            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                                                    FrameLayout.LayoutParams.MATCH_PARENT, imageHeight);//?????????????????????????????????????????????
                                            lp.bottomMargin = 10;//??????????????????
                                            imageView.setLayoutParams(lp);
                                        }
                                    }
                                });
                    } else {
                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(imagePath)
                                .placeholder(R.drawable.img_load_fail)
                                .error(R.drawable.img_load_fail)
                                .into(new TransformationScale(imageView));
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (subsInsert != null && subsInsert.isDisposed()) {
                subsInsert.dispose();
            }
            if (mDisposable != null && !mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
