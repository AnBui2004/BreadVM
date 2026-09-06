package hey.bread.vm.main.softwarestore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import hey.bread.vm.main.romstore.DataRoms;

import java.util.ArrayList;
import java.util.List;

public class SoftwareStoreViewModel extends ViewModel {
    private final MutableLiveData<List<DataRoms>> softwareList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<DataRoms>> getSoftwareList() {
        return softwareList;
    }

    public void setSoftwareList(List<DataRoms> data) {
        softwareList.setValue(data);
    }
}
