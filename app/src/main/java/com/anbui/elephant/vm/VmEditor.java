package com.anbui.elephant.vm;

import hey.bread.vm.AppConfig;
import hey.bread.vm.VMManager;
import hey.bread.vm.main.core.PendingCommand;
import hey.bread.vm.manager.VmFileManager;
import hey.bread.vm.manager.VmListManager;
import hey.bread.vm.utils.FileUtils;
import hey.bread.vm.utils.JSONUtils;

public class VmEditor {
    public static boolean handle() {
        if (
                PendingCommand.vmId == null ||
                PendingCommand.vmConfig == null ||
                PendingCommand.paramsNotebookConfig == null
        ) return false;

        return handle(PendingCommand.vmId, PendingCommand.vmConfig, PendingCommand.paramsNotebookConfig, PendingCommand.forceCreate);
    }

    public static boolean handle(String vmId, String vmConfig, String paramsNotebookConfig, boolean forceCreate) {
        if (!FileUtils.isFileExists(AppConfig.romsdatajson))
            if (!FileUtils.writeToFile(AppConfig.maindirpath, "roms-data.json", "[]")) return false;

        if (!JSONUtils.isValidFromFile(AppConfig.romsdatajson))  return false;

        String vmIdReady = vmId.isEmpty() ? VMManager.idGenerator() : vmId;
        if (!forceCreate && VMManager.isVMExist(vmIdReady)) {
            if (!VMManager.replaceToVMList(-1, vmIdReady , vmConfig)) return false;
        } else {
            if (!FileUtils.isEmpty(VmFileManager.quickGetPath(vmIdReady ))) vmIdReady  = VMManager.idGenerator();
            if (!VmListManager.isValidId(vmIdReady)) return false;
            if (!VMManager.addToVMList(vmConfig, vmIdReady)) return false;

            PendingCommand.forceCreate = true;
        }

        FileUtils.writeToFile(VmFileManager.getPath(vmIdReady), VmFileManager.CREATE_COMMAND_CONFIG_FILE_NAME, paramsNotebookConfig);

        return true;
    }
}
