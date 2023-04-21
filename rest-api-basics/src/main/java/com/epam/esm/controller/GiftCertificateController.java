package com.epam.esm.controller;

import com.epam.esm.entity.impl.GiftCertificate;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.service.interfaces.entity.GiftCertificateService;
import com.epam.esm.utils.search.dao.SearchParamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("gift/certificates")
public class GiftCertificateController {

    private final GiftCertificateService gcService;

    @Autowired
    public GiftCertificateController(GiftCertificateService gcService) {
        this.gcService = gcService;
    }

    @PostMapping("/create")
    public ResponseEntity<GiftCertificate> create(@RequestBody GiftCertificate gCert){
        gcService.create(gCert);
        return ResponseEntity.accepted().body(gCert);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> findById(@PathVariable Long id) {
        return ResponseEntity.accepted().body(gcService.findById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GiftCertificate> update(@RequestBody GiftCertificate gCert, @PathVariable Long id) {
        GiftCertificate original = gcService.findById(id);
        gCert.setId(original.getId());
        gcService.update(gCert);
        return ResponseEntity.accepted().body(gCert);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GiftCertificate> delete(@PathVariable Long id) {
        GiftCertificate gCert = gcService.findById(id);
        gcService.delete(id);
        return ResponseEntity.accepted().body(gCert);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<GiftCertificate>> findAll() {
        return ResponseEntity.accepted().body(gcService.findAll());
    }

    @GetMapping("/all/tagged")
    public ResponseEntity<Set<GiftCertificate>> findAllTagged() {
        return ResponseEntity.accepted().body(gcService.findAllTagged());
    }

    @GetMapping("/all/search")
    public ResponseEntity<Set<GiftCertificate>> findAllByParamSearch(SearchParamRequest spr, WebRequest webRequest) {
        for (Map.Entry<String, String[]> pm: webRequest.getParameterMap().entrySet()) {
            System.out.println(pm.getKey() + " " + Arrays.toString(pm.getValue()));
        }
        System.out.println(spr);
        return ResponseEntity.accepted().body(gcService.findAllParamSearch(spr).getItems());
    }

    @GetMapping("/{giftCertificateId}/tags")
    public ResponseEntity<Set<Tag>> findAllTagsByGiftCertificate(@PathVariable Long giftCertificateId){
        return ResponseEntity.accepted().body(gcService.findTagsByGiftCertificate(giftCertificateId));
    }

    @PutMapping("/{giftCertificateId}/tags/add/{tagId}")
    public ResponseEntity<GiftCertificate> addTag(@PathVariable Long giftCertificateId, @PathVariable Long tagId) {
        gcService.addTag(giftCertificateId, tagId);
        GiftCertificate gCert = gcService.findById(giftCertificateId);
        gCert.setTags(gcService.findTagsByGiftCertificate(giftCertificateId));
        return ResponseEntity.accepted().body(gCert);
    }

    @DeleteMapping("/{giftCertificateId}/tags/delete/{tagId}")
    public ResponseEntity<GiftCertificate> deleteTag(@PathVariable Long giftCertificateId, @PathVariable Long tagId) {
        gcService.deleteTag(giftCertificateId, tagId);
        GiftCertificate gCert = gcService.findById(giftCertificateId);
        gCert.setTags(gcService.findTagsByGiftCertificate(giftCertificateId));
        return ResponseEntity.accepted().body(gCert);
    }
}