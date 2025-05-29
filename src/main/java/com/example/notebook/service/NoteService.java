package com.example.notebook.service;

import com.example.notebook.dto.NoteDTO;
import com.example.notebook.model.Note;
import java.util.List;

public interface NoteService {


    Note createNoteForUser(String username, NoteDTO noteDTO);

    List<Note> getNotesForUser(String userName);

    Note updateNoteForUser(Long noteId, String userName, NoteDTO noteDTO);

    void deleteNoteForUser(Long noteId, String userName);
}
