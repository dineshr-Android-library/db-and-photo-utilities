package blogs.dinesh.com.everything;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.din.utils.Blog;
import com.din.utils.ImageHelper;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.BlogViewHolder> {

    public static class BlogViewHolder extends RecyclerView.ViewHolder/* implements View.OnClickListener*/ {


        CardView cv;
        TextView personName;
        TextView title;
        TextView description;
        ImageView personPhoto;


        BlogViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            title = (TextView)itemView.findViewById(R.id.title);
            description =(TextView)itemView.findViewById(R.id.description);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
//            itemView.setOnClickListener(this);
        }

      /* @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Choose the action");
            menu.add(Menu.NONE, R.id.,Menu.NONE,"Remove");
            menu.add(Menu.NONE, R.id.title, Menu.NONE,"Restore");
        }*/


       /* @Override
        public void onClick(View v) {

        }*/
    }


    private int position;
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    List<Blog> blogs;

    RVAdapter(List<Blog> blogs){
        this.blogs = blogs;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public BlogViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_activity, viewGroup, false);
        BlogViewHolder pvh = new BlogViewHolder(v);
        return pvh;
    }
    ImageHelper img = new ImageHelper();
    @Override
    public void onBindViewHolder(final BlogViewHolder blogViewHolder, int i) {

        blogViewHolder.personPhoto.setImageBitmap((Bitmap)img.byteToBitmap(blogs.get(i).photo));
        blogViewHolder.personName.setText(blogs.get(i).name);
        blogViewHolder.title.setText(blogs.get(i).title);
        blogViewHolder.description.setText(blogs.get(i).desc);

        blogViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(blogViewHolder.getAdapterPosition());
                return false;

            }
        });

    }

    @Override
    public int getItemCount() {
        return blogs.size();
    }


}
