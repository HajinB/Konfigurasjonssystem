package org.programutvikling.domain.utility;

import java.nio.file.Path;
import java.nio.file.Paths;

public class UserPreferences {

    final private String pathToAdminAppFiles = "AppFiles/Database/Admin/AdminAppData.jobj";
    final private String pathToCurrentComputer = "AppFiles/Database/User/TemporaryComputer/TemporaryComputer.txt";
    final private String pathToBackupAppFiles = "AppFiles/Database/Backup/AppDataBackup.jobj";
    final private String pathToComputers = "AppFiles/Database/User/Computers";

    /**
     * enduser:
     */
    public Path getPathPathToUserComputer() {
        return Paths.get(pathToCurrentComputer);
    }

    public String getStringPathToUserComputer() {
        return pathToCurrentComputer;
    }

    public Path getPathToComputers() {
        return Paths.get(pathToComputers);
    }

    /**
     * SuperUser:
     */
    public String getStringPath() {
        return pathToAdminAppFiles;
    }

    public Path getPathToAdminFiles() {
        return Paths.get(pathToAdminAppFiles);
    }

    public String getStringPathToBackupAppFiles() {
        return pathToBackupAppFiles;
    }


    public Path getPathToBackupAppFiles() {
        return Paths.get(pathToAdminAppFiles);
    }


}
