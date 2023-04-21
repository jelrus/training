package com.epam.esm.controller;

import com.epam.esm.entity.impl.GiftCertificate;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.service.interfaces.entity.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/create")
    public ResponseEntity<Tag> create(@RequestBody Tag tag){
        tagService.create(tag);
        return ResponseEntity.accepted().body(tag);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> findById(@PathVariable Long id) {
        return ResponseEntity.accepted().body(tagService.findById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Tag> delete(@PathVariable Long id) {
        Tag tag = tagService.findById(id);
        tagService.delete(id);
        return ResponseEntity.accepted().body(tag);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<Tag>> findAll() {
        return ResponseEntity.accepted().body(tagService.findAll());
    }

    @GetMapping("/all/tagged")
    public ResponseEntity<Set<Tag>> findAllCertificated() {
        return ResponseEntity.accepted().body(tagService.findAllCertificated());
    }

    @GetMapping("/tag/{tagId}/gift/certificates")
    public ResponseEntity<Set<GiftCertificate>> findAllGiftCertificatesByTag(@PathVariable Long tagId){
        return ResponseEntity.accepted().body(tagService.findGiftCertificatesByTag(tagId));
    }

    @PutMapping("/{tagId}/gift/certificates/add/{giftCertificateId}")
    public ResponseEntity<Tag> addTag(@PathVariable Long tagId, @PathVariable Long giftCertificateId) {
        tagService.addGiftCertificate(tagId, giftCertificateId);
        Tag tag = tagService.findById(tagId);
        tag.setGiftCertificates(tagService.findGiftCertificatesByTag(tagId));
        return ResponseEntity.accepted().body(tag);
    }

    @DeleteMapping("/{tagId}/gift/certificates/delete/{giftCertificateId}")
    public ResponseEntity<Tag> deleteTag(@PathVariable Long tagId, @PathVariable Long giftCertificateId) {
        tagService.deleteGiftCertificate(tagId, giftCertificateId);
        Tag tag = tagService.findById(tagId);
        tag.setGiftCertificates(tagService.findGiftCertificatesByTag(tagId));
        return ResponseEntity.accepted().body(tag);
    }
}