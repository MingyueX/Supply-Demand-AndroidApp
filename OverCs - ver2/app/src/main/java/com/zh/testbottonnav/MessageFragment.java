package com.zh.testbottonnav;

import androidx.fragment.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import io.rong.imkit.RongIM;
import io.rong.imkit.conversation.ConversationFragment;
import io.rong.imkit.conversationlist.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class MessageFragment extends Fragment {
    private Fragment mConversationFragment = null;
    private Fragment mConversationList;
    //private ViewPager vpContent;
    //private List<Fragment> mFragments = new ArrayList<>();
    //private FragmentPagerAdapter adapter;
    private View myview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.messagepage, container, false);
        return myview;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.conversationlist, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mConversationList = initConversationList();//融云会话列表的对象
        //mFragments.add(mConversationList);//添加会话fragment
        /*
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        vpContent.setAdapter(adapter);

         */

        //Fragment childFragment = new ChildFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.vp_content, mConversationList).commit();
    }


    //集成会话列表
    public Fragment initConversationList(){
        if(mConversationFragment == null){
            ConversationListFragment conversationListFragment = new ConversationListFragment();
            /*
            Uri uri = Uri.parse("rong://" + getContext().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                    .build();
            conversationListFragment.setUri(uri);
             */
            return conversationListFragment;
        }else{
            return mConversationFragment;
        }
    }

    public static MessageFragment newInstance(String string) {

        Bundle args = new Bundle();
        args.putString("Topic", string);

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
