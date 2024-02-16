package org.example.seabattle.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.example.seabattle.repositories.api.AdminRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {

    @Override
    public void deleteGame(String fileName) {
        File file = new File(".\\fight\\" + fileName);
        if (file.delete()) {
            System.out.println("File successfully deleted");
        }
    }

    @Override
    public void archiveGame(String fileName) {
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(fileName + ".zip"));
            FileInputStream fis= new FileInputStream("./fight/" + fileName);)
        {
            ZipEntry zipEntry=new ZipEntry(fileName);
            zout.putNextEntry(zipEntry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        }
        catch(Exception ex){
            System.out.println("File not found");
        }
    }
}
