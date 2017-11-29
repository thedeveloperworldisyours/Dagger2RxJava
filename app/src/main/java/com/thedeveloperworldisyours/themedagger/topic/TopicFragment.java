package com.thedeveloperworldisyours.themedagger.topic;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.thedeveloperworldisyours.themedagger.R;
import com.thedeveloperworldisyours.themedagger.data.Topics;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class TopicFragment extends Fragment implements TopicContract.View, TopicAdapter.MyClickListener {

    @BindView(R.id.theme_fragment_progress)
    ProgressBar mProgressBar;

    @BindView(R.id.theme_fragment_retry_button)
    Button mRetry;

    @BindView(R.id.theme_fragment_constraintLayout)
    ConstraintLayout mRelativeLayout;

    @BindView(R.id.theme_fragment_recycler_view)
    RecyclerView mRecyclerView;

    List<Topics> mListTopics;

//    private SharedPreference mSharedPreference;
    private TopicContract.Presenter mPresenter;

    public TopicFragment() {
        // Required empty public constructor
    }

    public static TopicFragment newInstance() {
        return new TopicFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.theme_fragment, container, false);
        ButterKnife.bind(this, view);
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void setPresenter(TopicContract.Presenter presenter) {
        mPresenter = presenter;
    }

        @OnClick(R.id.theme_fragment_retry_button)
        public void retry(View view) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRetry.setVisibility(View.GONE);
            mPresenter.fetch();
        }

    @Override
    public void showTopics(List<Topics> list) {
        mListTopics = list;
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TopicAdapter adapter = new TopicAdapter(getActivity(), list);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void showError() {
        mProgressBar.setVisibility(View.GONE);
        mRetry.setVisibility(View.VISIBLE);
        Snackbar.make(mRelativeLayout, getActivity().getResources().getText(R.string.error_server).toString(), LENGTH_LONG).show();
        mRetry.setText(getString(R.string.retry));
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (!active) {
            mRetry.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void onItemClick(int position, View v) {
//        startActivity(new Intent(getActivity(), LevelActivity.class));
    }

}
