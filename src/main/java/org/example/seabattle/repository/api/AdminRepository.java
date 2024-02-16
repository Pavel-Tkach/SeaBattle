package org.example.seabattle.repository.api;

public interface AdminRepository {
    void deleteGame(String fileName);
    void archiveGame(String fileName);
}
