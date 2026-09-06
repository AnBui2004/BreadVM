package hey.bread.vm.utils;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import hey.bread.vm.R;

public class ClipboardUltils {
    public static void copyToClipboard(Context context, String text) {
        copyToClipboard(context, text, true);
    }

    public static void copyToClipboard(Context context, String text, boolean showToast) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("From Bread VM", text);
        assert clipboard != null;
        clipboard.setPrimaryClip(clip);
        if (showToast) Toast.makeText(context, context.getString(R.string.copied), Toast.LENGTH_SHORT).show();
    }
}
