package in.yourstreet.permissionmanager;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AboutTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AboutTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View v;

    private TextView versionTextView;
    private TextView descTextView;
    private TextView yourStreetTextView;
    private TextView playStoreLink;
    private TextView githubLink;
    private TextView twitterLink;
    private TextView facebookLink;
    private Button rateAppButton;
    private Button contactUsButton;
    private Button shareAppButton;
    private OnFragmentInteractionListener mListener;

    public AboutTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutTab.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutTab newInstance(String param1, String param2) {
        AboutTab fragment = new AboutTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_about_tab, container, false);
        versionTextView = v.findViewById(R.id.version_textview);
        String version;
        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version = "1.x";
            e.printStackTrace();
        }
        versionTextView.setText("Version "+version);

        descTextView = v.findViewById(R.id.desc_textview);
        String text = "<b> Permission Manager</b> is a free and open-source application developed by" +
                " <a href=\"https://www.yourstreet.in/\">YourStreet</a>." +
                " You can find and experiment with the code in this " +
                " <a href=\"https://github.com/YourStreet/Android-Permission-Manager\">Github Repo</a>.";
        descTextView.setText(getHTML(text));
        descTextView.setMovementMethod(LinkMovementMethod.getInstance());

        yourStreetTextView = (TextView) v.findViewById(R.id.your_street_text);

        playStoreLink = (TextView) v.findViewById(R.id.playstore);
        text = "<a href=\"https://play.google.com/store/apps/developer?id=Your+Street\">Play Store</a>";
        playStoreLink.setText(getHTML(text));
        playStoreLink.setMovementMethod(LinkMovementMethod.getInstance());

        githubLink = (TextView) v.findViewById(R.id.github);
        text = "<a href=\"https://github.com/YourStreet\">Github</a>";
        githubLink.setText(getHTML(text));
        githubLink.setMovementMethod(LinkMovementMethod.getInstance());

        twitterLink = (TextView) v.findViewById(R.id.twitter);
        text = "<a href=\"https://twitter.com/YourStreetOrg\">Twitter</a>";
        twitterLink.setText(getHTML(text));
        twitterLink.setMovementMethod(LinkMovementMethod.getInstance());

        facebookLink = (TextView) v.findViewById(R.id.facebook);
        text = "<a href=\"https://www.facebook.com/YourStreetOrg\">Facebook</a>";
        facebookLink.setText(getHTML(text));
        facebookLink.setMovementMethod(LinkMovementMethod.getInstance());

        rateAppButton = (Button) v.findViewById(R.id.rate_app_button);
        rateAppButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e("Hello","Rate App");
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
            }
        });

        shareAppButton = (Button) v.findViewById(R.id.share_app_button);
        shareAppButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e("Hello","Share App");
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Permission Manager");
                    String shareMessage= "Easily check and manage permissions \n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    e.toString();
                }
            }
        });

        contactUsButton = (Button) v.findViewById(R.id.contact_us_button);
        contactUsButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e("Hello","Contact Us");
                String subject = "Feedback or Suggestion for YourStreet";
                String bodyText = "Write any thing you love.";
                String mailto = "mailto:contact@yourstreet.in" +
                        "?cc=" + "hmp210@gmail.com" +
                        "&subject=" + Uri.encode(subject) +
                        "&body=" + Uri.encode(bodyText);

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    //TODO: Handle case where no email app is available
                }
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    Spanned getHTML(String text)
    {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(text,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(text);
        }
        return  result;
    }
}
