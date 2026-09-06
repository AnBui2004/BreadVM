package hey.bread.vm.main.vms;

import androidx.recyclerview.widget.DiffUtil;

import hey.bread.vm.main.core.MainConfigs;
import hey.bread.vm.manager.VmListManager;

import java.util.List;

public class VmsDiffUtil extends DiffUtil.Callback {
    private final List<DataMainRoms> oldList;
    private final List<DataMainRoms> newList;

    public VmsDiffUtil(List<DataMainRoms> oldList, List<DataMainRoms> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) != null && newList.get(newItemPosition) != null && oldList.get(oldItemPosition).vmID.equals(newList.get(newItemPosition).vmID);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        DataMainRoms oldItem = oldList.get(oldItemPosition);
        DataMainRoms newItem = newList.get(newItemPosition);
        if (oldItem == null || newItem == null) return false;
        return !MainConfigs.refreshVmIds.contains(newItem.vmID) && VmListManager.isSameVmConfig(oldItem, newItem);
    }
}

