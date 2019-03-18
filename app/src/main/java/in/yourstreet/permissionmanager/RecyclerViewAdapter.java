package in.yourstreet.permissionmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<AppItem> mData;
    public static final int REQUEST_CODE = 111;
    public static String uninstallingAppName;
    public static int removedAppIndex;
    public RecyclerViewAdapter(Context mContext, List<AppItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_app,viewGroup,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final AppItem ap = mData.get(i);
        myViewHolder.appName.setText(ap.getAppName());
        myViewHolder.appImage.setImageDrawable(ap.getAppIcon());
        myViewHolder.imageUninstall.setVisibility(ImageView.INVISIBLE);
        myViewHolder.imageSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", ap.getPackageName(), null);
                intent.setData(uri);
                mContext.startActivity(intent);
            }
        });
        myViewHolder.imageUninstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uninstallingAppName = ap.getPackageName();
                removedAppIndex = ap.getAppIndex();
                Uri packageUri = Uri.parse("package:"+ap.getPackageName());
                PermissionCommonTabFragment.uninstallingPackageName = ap.getPackageName();
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,packageUri);
                ((Activity) mContext).startActivityForResult(uninstallIntent,REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mData == null)
            return  0;
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView appImage;
        private TextView appName;
        private ImageView imageSetting;
        private ImageView imageUninstall;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            appImage = (ImageView) itemView.findViewById(R.id.image_app);
            appName = (TextView) itemView.findViewById(R.id.app_name);
            imageSetting = (ImageView) itemView.findViewById(R.id.image_setting);
            imageUninstall = (ImageView) itemView.findViewById(R.id.image_uninstall);

        }
    }

}
