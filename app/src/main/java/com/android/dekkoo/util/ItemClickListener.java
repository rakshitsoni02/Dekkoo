package com.android.dekkoo.util;

import java.util.List;

public interface ItemClickListener {
    void tittleItemSelected(int position, List<String> values, String tittle);

    void valueItemSelected(int position, String value);
}
