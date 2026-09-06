package hey.bread.vm.main.core;

import hey.bread.vm.main.romstore.DataRoms;
import hey.bread.vm.main.vms.DataMainRoms;

import java.util.ArrayList;
import java.util.List;

public class SharedData {
    public static List<DataMainRoms> dataVms = new ArrayList<>();
    public static List<DataRoms> dataRomStore = new ArrayList<>();
    public static List<DataRoms> dataSoftwareStore = new ArrayList<>();
}
