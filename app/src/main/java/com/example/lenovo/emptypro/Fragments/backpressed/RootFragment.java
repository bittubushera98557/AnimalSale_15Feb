package com.example.lenovo.emptypro.Fragments.backpressed;


import androidx.fragment.app.Fragment;

public class RootFragment extends Fragment implements OnBackPressListener {

    @Override
    public boolean onBackPressed() {
        return new BackPressImpl(this).onBackPressed();
    }
}
