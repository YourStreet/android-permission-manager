package in.yourstreet.permissionmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PermissionCommonTabFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PermissionCommonTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PermissionCommonTabFragment extends Fragment {

    private static final String ARG_PARAM1 = "PERMISSION_NUMBER";

    private String mParam1;
    private int permissionIndex;
    private int numberOfApps;
    private String numberAppText;
    public static String uninstallingPackageName;


    View v;

    private RecyclerView recyclerView;
    private TextView numberTextView;
    private List<AppItem> listApps;

    private OnFragmentInteractionListener mListener;

    public PermissionCommonTabFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String param1) {
        PermissionCommonTabFragment fragment = new PermissionCommonTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            permissionIndex = Integer.parseInt(mParam1);
        }
        listApps = new ArrayList<>();
        for(Integer x:MainActivity.permissionSets.get(permissionIndex))
        {
            listApps.add(MainActivity.staticAllApps.get(x));
        }
        numberOfApps = listApps.size();
        numberAppText = numberOfApps + " applications are using "+ ConstantParameters.PermissionTabName[permissionIndex];

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_permission_common_tab, container, false);

        numberTextView = (TextView) v.findViewById(R.id.total_number_app);
        numberTextView.setText(numberAppText);
        recyclerView = (RecyclerView) v.findViewById(R.id.app_recyclerview);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),listApps);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
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

}
