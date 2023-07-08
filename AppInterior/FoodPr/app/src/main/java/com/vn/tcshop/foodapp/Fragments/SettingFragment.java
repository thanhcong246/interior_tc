package com.vn.tcshop.foodapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.vn.tcshop.foodapp.Activitis.Accounts.LoginActivity;
import com.vn.tcshop.foodapp.Activitis.Carts.CartActivity;
import com.vn.tcshop.foodapp.Activitis.Historys.HistoryActivity;
import com.vn.tcshop.foodapp.Activitis.HomeActivity;
import com.vn.tcshop.foodapp.Activitis.MainActivity;
import com.vn.tcshop.foodapp.Activitis.Products.ProductActivity;
import com.vn.tcshop.foodapp.Activitis.Settings.SettingActivity;
import com.vn.tcshop.foodapp.Adapters.MenuAdapter;
import com.vn.tcshop.foodapp.Models.MenuItemModel;
import com.vn.tcshop.foodapp.R;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends Fragment {
    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private List<MenuItemModel> menuItems;
    private static final String SHARED_PREFS_NAME = "login_prefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER_ME = "remember_me";
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        setHasOptionsMenu(true); // Bật chế độ hiển thị menu

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getActivity(),gso);

        menuItems = getMenuItems();

        // Thiết lập RecyclerView và MenuAdapter
        recyclerView = view.findViewById(R.id.setting_recyclerView_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        menuAdapter = new MenuAdapter(menuItems);
        recyclerView.setAdapter(menuAdapter);

        menuAdapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MenuItemModel menuItem) {
                // Xử lý sự kiện khi một mục trong menu được chọn
                String itemId = menuItem.getTitle();

                // Kiểm tra ID của mục menu và chuyển đến trang tương ứng
                switch (itemId) {
                    case "Trang chủ":
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                        break;
                    case "Sản phẩm":
                        startActivity(new Intent(getActivity(), ProductActivity.class));
                        break;
                    case "Giỏ hàng":
                        startActivity(new Intent(getActivity(), CartActivity.class));
                        break;
                    case "Thông báo":
                        break;
                    case "Lịch sử giao dịch":
                        startActivity(new Intent(getActivity(), HistoryActivity.class));
                        break;
                    case "Trò chuyện":
                        linkChatFB();
                        break;
                    case "Câu hỏi thường gặp":
                        break;
                    case "Đăng xuất":
                        logout_btn();
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }

    private void linkChatFB() {
        String pageId = "100090595422680";
        String messengerUrl = "https://m.me/" + pageId;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(messengerUrl));
        startActivity(intent);
    }

    private void logout_btn() {
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

    private List<MenuItemModel> getMenuItems() {
        List<MenuItemModel> items = new ArrayList<>();

        // Đọc danh sách danh mục từ menu.xml và thêm vào items
        Context context = requireContext(); // Sử dụng requireContext() thay vì getContext()
        MenuInflater inflater = new MenuInflater(context);
        @SuppressLint("RestrictedApi") Menu menu = new MenuBuilder(context);
        inflater.inflate(R.menu.setting_fragment, menu);

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