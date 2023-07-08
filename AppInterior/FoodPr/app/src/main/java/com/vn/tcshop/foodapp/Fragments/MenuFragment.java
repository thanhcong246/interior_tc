package com.vn.tcshop.foodapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.vn.tcshop.foodapp.Activitis.Accounts.LoginActivity;
import com.vn.tcshop.foodapp.Activitis.MainActivity;
import com.vn.tcshop.foodapp.Adapters.MenuAdapter;
import com.vn.tcshop.foodapp.Activitis.Carts.CartActivity;
import com.vn.tcshop.foodapp.Models.MenuItemModel;
import com.vn.tcshop.foodapp.Activitis.Products.ProductActivity;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Activitis.Settings.SettingActivity;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {
    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private List<MenuItemModel> menuItems;

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "login_prefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private TextView fragment_home_menu_user_tv, fragment_home_menu_email_tv;
    private TextView btn_logout;
    private ImageView btn_close_fragment_menu;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        setHasOptionsMenu(true); // Bật chế độ hiển thị menu

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getActivity(), gso);

        // Khởi tạo danh sách danh mục từ menu.xml
        menuItems = getMenuItems();

        // Thiết lập RecyclerView và MenuAdapter
        recyclerView = view.findViewById(R.id.recyclerView_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        menuAdapter = new MenuAdapter(menuItems);
        recyclerView.setAdapter(menuAdapter);

        fragment_home_menu_user_tv = view.findViewById(R.id.fragment_home_menu_user_tv);
        fragment_home_menu_email_tv = view.findViewById(R.id.fragment_home_menu_email_tv);
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_close_fragment_menu = view.findViewById(R.id.btn_close_fragment_menu);


        menuAdapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MenuItemModel menuItem) {
                // Xử lý sự kiện khi một mục trong menu được chọn
                String itemId = menuItem.getTitle();

                // Kiểm tra ID của mục menu và chuyển đến trang tương ứng
                switch (itemId) {
                    case "Trang chủ":
                        break;
                    case "Sản phẩm":
                        startActivity(new Intent(getActivity(), ProductActivity.class));
                        break;
                    case "Giỏ hàng":
                        startActivity(new Intent(getActivity(), CartActivity.class));
                        break;
                    case "Thông báo":
                        break;
                    case "Cài đặt":
                        startActivity(new Intent(getActivity(), SettingActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });

        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)) {
            String savedName = sharedPreferences.getString(KEY_NAME, "");
            String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");

            fragment_home_menu_user_tv.setText(savedName);
            fragment_home_menu_email_tv.setText(savedEmail);

            logout_btn();

        }

        close_menu();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_home, menu);
    }

    private void close_menu() {
        btn_close_fragment_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.popBackStack();
                }
            }
        });
    }


    private void logout_btn() {
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        // Khi đăng xuất
                        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(KEY_EMAIL);
                        editor.remove(KEY_PASSWORD);
                        editor.remove(KEY_REMEMBER_ME);
                        editor.apply();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                });
            }
        });
    }

    private List<MenuItemModel> getMenuItems() {
        List<MenuItemModel> items = new ArrayList<>();

        // Đọc danh sách danh mục từ menu.xml và thêm vào items
        Context context = requireContext(); // Sử dụng requireContext() thay vì getContext()
        MenuInflater inflater = new MenuInflater(context);
        @SuppressLint("RestrictedApi") Menu menu = new MenuBuilder(context);
        inflater.inflate(R.menu.menu_fragment_home, menu);

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            int id = item.getItemId();
            String title = item.getTitle().toString();
            Drawable iconDrawable = item.getIcon();

            MenuItemModel menuItemModel = new MenuItemModel(id, title, iconDrawable); // Truyền trực tiếp drawable
            items.add(menuItemModel);
        }

        return items;
    }
}
