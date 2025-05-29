package com.example.notebook.serviceImpl;

import com.example.notebook.dto.NoteDTO;
import com.example.notebook.model.Note;
import com.example.notebook.repository.NoteRepository;
import com.example.notebook.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Note createNoteForUser(String username, NoteDTO noteDTO) {
        Note newNote = new Note();
        newNote.setNoteContent(noteDTO.getContent());
        newNote.setNoteTitle(noteDTO.getTitle());
        newNote.setOwnerUsername(username);
        return noteRepository.save(newNote) ;
    }

    @Override
    public List<Note> getNotesForUser(String userName) {
        return noteRepository.findByOwnerUsername(userName);
    }

    @Override
    public Note updateNoteForUser(Long noteId, String userName, NoteDTO noteDTO) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        note.setNoteContent(noteDTO.getContent());
        note.setNoteTitle(noteDTO.getTitle());
        return noteRepository.save(note);
    }

    @Override
    public void deleteNoteForUser(Long noteId, String userName) {
        noteRepository.deleteById(noteId);
    }
}
