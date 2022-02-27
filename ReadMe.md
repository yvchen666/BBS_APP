### 项目引用：
    Toast：com.github.GrenderG:Toasty:1.5.2
    BottomNavigationView：com.github.ittianyu:BottomNavigationViewEx:2.0.4
    Banner：io.github.youth5201314:banner:2.2.2



### 项目进度：
    网络请求框架
    登陆注册 主界面编写
    recyclerview加载不同样式
    我的界面
### Todo
    recyclerView上滑banner消失
    帖子详情
    发帖界面
        发帖 @逻辑
    后期再加 话题界面
    注册界面


### 经验：
    使用约束布局时 宽高设为0dp 然后进行调整 解决recyclerView显示不全的问题
    大图片放入mipmap 加载起来内存优化比draw able好很多

    RecyclerView，item中的xml布局是match_parent，但是宽度没能铺满整个界面解决办法：
        ``` java
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //        View itemView = View.inflate(context,R.layout.item_recycle,null);
            //        return new ViewHolder(itemView);
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_list, parent, false);//解决宽度不能铺满
                    ViewHolder holder = new ViewHolder(view);
                    return holder;
            }
        ```

