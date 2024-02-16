package org.example.seabattle.repositories.api;

public interface AdminRepository {
    void deleteGame(String fileName);
    void archiveGame(String fileName);
}
