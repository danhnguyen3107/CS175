import android.app.assist.AssistStructure;
import android.os.CancellationSignal;
import android.service.autofill.AutofillService;
import android.service.autofill.FillCallback;
import android.service.autofill.FillContext;
import android.service.autofill.FillRequest;
import android.service.autofill.FillResponse;
import android.service.autofill.SaveRequest;
import android.service.autofill.SaveCallback;
import android.view.autofill.FillContext;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import java.util.List;

public class MyAutofillService extends AutofillService {

    @Override
    public void onFillRequest(@NonNull FillRequest request, @NonNull CancellationSignal cancellationSignal, @NonNull FillCallback callback) {

    }

    @Override
    public void onSaveRequest(@NonNull SaveRequest request, @NonNull SaveCallback callback) {
        // Handle saving autofill data
        callback.onSuccess();
    }
}
