package ue.edu.co.volleyapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {

    private final LayoutInflater inflater;

    public PostAdapter(Context context, List<Post> posts) {
        super(context, 0, posts);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_post, parent, false);
        }

        Post post = getItem(position);

        TextView tvUserId = view.findViewById(R.id.tvUserId);
        TextView tvId = view.findViewById(R.id.tvId);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvBody = view.findViewById(R.id.tvBody);

        tvUserId.setText(getContext().getString(R.string.tv_user_id, post.getUserId()));
        tvId.setText(getContext().getString(R.string.tv_id, post.getId()));
        tvTitle.setText(post.getTitle());
        tvBody.setText(post.getBody());

        return view;
    }
}