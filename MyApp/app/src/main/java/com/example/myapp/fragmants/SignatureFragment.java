package com.example.myapp.fragmants;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Common.Utility;
import com.example.myapp.R;
import com.example.myapp.async.ImageAsync;
import com.example.myapp.databinding.ActivityMainBinding;
import com.example.myapp.dbhelper.SurveyDataHelper;
import com.example.myapp.dbhelper.SurveyDataSource;
import com.example.myapp.listner.ImageCallbackListener;
import com.example.myapp.listner.PhotoCompressedListener;
import com.example.myapp.listner.SignatureCallbackListener;
import com.example.myapp.model.SurveyDataModel;
import com.inventia.ugo_mici.app.MyApplication;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SignatureFragment extends Fragment implements View.OnClickListener {

    private   ActivityMainBinding activityMainBinding;
    private static final String TAG = "SuccessFragment";
    private FragmentActivity mActivity;
    private TextView  lblSign;
    private Button btnDone, btnReset;
    private FrameLayout canvasLayout;
    private DrawingView myView;
    private boolean status = false;
    private Paint mPaint;
    private SurveyDataModel surveyDataModel;
    private int originalMode;
    private String unique_id;
    public static SignatureCallbackListener signatureCallbackListener1;

    public SignatureFragment( String unique_id)
    {
        this.unique_id=unique_id;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        originalMode = mActivity.getWindow().getAttributes().softInputMode;

        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // bottom_Navigation.setVisibility(View.GONE);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signature, container, false);

        surveyDataModel = SurveyDataHelper.Companion.getByUniqueId(unique_id, mActivity);


        lblSign = (TextView) view.findViewById(R.id.lblSign);
        canvasLayout = (FrameLayout) view.findViewById(R.id.canvasLayout);

        btnDone = (Button) view.findViewById(R.id.btnDone);
        btnReset = (Button) view.findViewById(R.id.btnReset);


        myView = new DrawingView(mActivity);
        canvasLayout.addView(myView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        canvasLayout.setDrawingCacheEnabled(true);
        canvasLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        canvasLayout.layout(0, 0, canvasLayout.getMeasuredWidth(), canvasLayout.getMeasuredHeight());
        canvasLayout.buildDrawingCache();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        //  mPaint.setColor(Color.parseColor("#da3a3d"));
        mPaint.setColor(Color.parseColor("#4d387e"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);

        btnDone.setOnClickListener(this);
        btnReset.setOnClickListener(this);


        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @NotNull MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mActivity = (FragmentActivity) context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (originalMode != 0)
            mActivity.getWindow().setSoftInputMode(originalMode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone:
                savePhoto();
                break;
            case R.id.btnReset:
                lblSign.setVisibility(View.VISIBLE);
                status = false;
                if (myView.mBitmap != null) {
                    myView.mBitmap.eraseColor(Color.TRANSPARENT);
                    myView.mPath.reset();
                    myView.invalidate();
                }
                break;
        }
    }

    // save signature in database
    private void savePhoto() {
        // TODO: 28-11-2017 signature is taken from consumer

      /*  if (getStringValue(edtReceiverName.getText().toString()) == null) {
           // Utility.snackBar(layout_root, getActivity().getResources().getString(R.string.emptyreceiver), 1200, getResources().getColor(R.color.warning));
            layoutReceiver.setBackgroundResource(R.drawable.layout_selected);
            return;
        }*/
        if (status) {

            try {
                Bitmap SignedPhoto = myView.mBitmap;
                String signatureFileName = "IMG_" + surveyDataModel.getPROPERTY_NO() + "_Sign.png";
                final boolean cachePreviousState = canvasLayout.isDrawingCacheEnabled();
                final int backgroundPreviousColor = canvasLayout.getDrawingCacheBackgroundColor();
                canvasLayout.setDrawingCacheEnabled(true);
                canvasLayout.setDrawingCacheBackgroundColor(0xfffafafa);
                final Bitmap bitmap = canvasLayout.getDrawingCache();
                canvasLayout.setDrawingCacheBackgroundColor(backgroundPreviousColor);
                if (SignedPhoto != null) {
                    File ConsumerSignature = convertBitmapToFile(mActivity, bitmap, signatureFileName);
                    FileOutputStream outStream = null;
                    try {
                        outStream = new FileOutputStream(ConsumerSignature);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(bitmap!=null) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 80, outStream);
                        canvasLayout.setDrawingCacheEnabled(cachePreviousState);
                    }
                    if (ConsumerSignature.exists()) {

                        signatureCallbackListener1.signatureCallback(ConsumerSignature);
                        mActivity.finish();
                        //new ImageAsync(path, true).execute();
//                        new ImageAsync(mActivity, ConsumerSignature, false, surveyDataModel, true,
//                                new PhotoCompressedListener() {
//                                    @Override
//                                    public void compressedPhoto(String path) {
//
//                                        surveyDataModel.setISVISITED(true);
//                                        surveyDataModel.setISUPLOADED(false);
//
//                                        boolean isSave = SurveyDataHelper.Companion.saveSuvreyData(surveyDataModel, mActivity);
//                                        if (isSave) {
//                                            clearAllFragment(mActivity);
//                                            replaceFragment(new SurveyCompleteFragment(activityMainBinding), mActivity.getSupportFragmentManager(), R.id.layout_fragment);
//
//                                        }
//
//                                    }
//                                }).execute();

                    } else {
                        Toast.makeText(mActivity,"Signature can't be blank",Toast.LENGTH_SHORT).show();

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
               // Utility.saveError(MyApplication.getInstance(), accountNo, "SuccessFragmentError", e.getMessage());
            }




        } else {

            Toast.makeText(mActivity,"Signature can't be blank",Toast.LENGTH_SHORT).show();

        }
    }

    public static void replaceFragment(Fragment fragment, FragmentManager fragmentManager, int resId) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //   fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(resId, fragment);
            // fragmentTransaction.commit();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();

        }
    }

    public static void clearAllFragment(FragmentActivity fragmentActivity) {
        FragmentManager fm = fragmentActivity.getSupportFragmentManager(); // or 'getSupportFragmentManager();'
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStack();
        }
    }

    public static String getStringValue(String value) {
        String str = value;
        try {
            if (str.isEmpty() || str.toLowerCase().equals("null"))
                str = null;
        } catch (Exception e) {
        }
        return str;
    }

    // class for draw signature
    private class DrawingView extends View {

        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;

        public DrawingView(Context context) {
            super(context);
            this.context = context;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.parseColor("#da3a3d"));
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
            canvas.drawPath(circlePath, circlePaint);

        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            mCanvas.drawPath(mPath, mPaint);
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            lblSign.setVisibility(GONE);
            status = true;
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
    }

    public static File convertBitmapToFile(Context context, Bitmap bitmap, String imageFileName) {
        File f = null;
        try {
            f = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageFileName);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }


    public static void getSignatureCallback(SignatureCallbackListener signatureCallbackListener){
        signatureCallbackListener1 = signatureCallbackListener;
    }
}