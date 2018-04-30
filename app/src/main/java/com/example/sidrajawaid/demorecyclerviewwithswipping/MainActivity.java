package com.example.sidrajawaid.demorecyclerviewwithswipping;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static int firstVisibleInListview;
    Button btn;
    RecyclerView recyclerView;
    RecyclerAdapterClass recyclerAdapterClass;
    ArrayList savedarrayList=new ArrayList();
    ProgressBar progressBar;
    private boolean b_loading=true;
    private boolean t_loading=true;
    SwipeRefreshLayout swipeRefreshLayout;
    Paint paint=new Paint();
    private int completeVisibleItem, totalItemCount,visibleItemCount;
    LinearLayoutManager linearLayoutManager=null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv);
        btn = findViewById(R.id.btn);
        progressBar=findViewById(R.id.prg);
        swipeRefreshLayout=findViewById(R.id.srl);
        final ArrayList<ModelClass> arrayList = new ArrayList<>();
        arrayList.add(new ModelClass(R.drawable.image1, "Default1", "123"));
        arrayList.add(new ModelClass(R.drawable.image2, "Default2", "Pakistan"));
        arrayList.add(new ModelClass(R.drawable.image1, "Default3", "123"));
        arrayList.add(new ModelClass(R.drawable.image2, "Default4", "Pakistan"));
        arrayList.add(new ModelClass(R.drawable.image1, "Default5", "123"));
        recyclerAdapterClass = new RecyclerAdapterClass(this, arrayList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapterClass);
        savedarrayList = ((RecyclerAdapterClass) recyclerView.getAdapter()).getArrayList();
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle b = new Bundle();
                b.putSerializable("array", savedarrayList);
                SavedViews saved_Views = new SavedViews();
                saved_Views.setArguments(b);
                fragmentTransaction.replace(R.id.framelayout, saved_Views);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.isRefreshing();
                        Toast.makeText(MainActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
                        arrayList.add(0, new ModelClass(R.drawable.image2, "Top1", "Pakistan"));
                        arrayList.add(1, new ModelClass(R.drawable.image1, "Top2", "123"));
                        arrayList.add(2, new ModelClass(R.drawable.image2, "Top3", "Pakistan"));
                        recyclerAdapterClass = new RecyclerAdapterClass(MainActivity.this, arrayList);
                        recyclerView.setAdapter(recyclerAdapterClass);
                        savedarrayList = ((RecyclerAdapterClass) recyclerView.getAdapter()).getArrayList();
                        recyclerAdapterClass.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });
       recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    completeVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                    if (b_loading)
                    {
                        if ((visibleItemCount+completeVisibleItem)>=totalItemCount) {
                            progressBar.setVisibility(View.VISIBLE);
                            b_loading=false;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Scrolling up", Toast.LENGTH_SHORT).show();
                                    arrayList.add(new ModelClass(R.drawable.image1, "Bottom1", "123"));
                                    arrayList.add(new ModelClass(R.drawable.image2, "Bottom2", "Pakistan"));
                                    arrayList.add(new ModelClass(R.drawable.image1, "Bottom3", "123"));
                                    arrayList.add(new ModelClass(R.drawable.image2, "Bottom4", "Pakistan"));
                                    recyclerAdapterClass=new RecyclerAdapterClass(MainActivity.this,arrayList);
                                    recyclerView.setAdapter(recyclerAdapterClass);
                                    savedarrayList = ((RecyclerAdapterClass) recyclerView.getAdapter()).getArrayList();
                                    recyclerAdapterClass.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                }
                            },2000);
                        }
                    }
                }
            }
        });
        itemMethod();
    }
    public void itemMethod()
    {
        ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    recyclerAdapterClass.dismissView(position);
                    Toast.makeText(MainActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                    recyclerAdapterClass.notifyDataSetChanged();
                } else if (direction == ItemTouchHelper.RIGHT) {
                    Toast.makeText(MainActivity.this,"Saved",Toast.LENGTH_SHORT).show();
                    recyclerAdapterClass.addView(position);
                    recyclerAdapterClass.notifyDataSetChanged();
                }
            }
            /*public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        paint.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,paint);
                    } else {
                        paint.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,paint);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }*/
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}




