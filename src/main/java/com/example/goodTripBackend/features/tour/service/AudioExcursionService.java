//package com.example.goodTripBackend.features.tour.service;
//
//import com.example.goodTripBackend.features.tour.models.dto.AudioExcursionDto;
//import com.example.goodTripBackend.features.tour.models.entities.AudioExcursion;
//import com.example.goodTripBackend.features.tour.models.mapper.MapperAudioExcursion;
//import com.example.goodTripBackend.features.tour.repository.AddressRepository;
//import com.example.goodTripBackend.features.tour.repository.AudioExcursionRepository;
//import com.example.goodTripBackend.features.user.service.UserService;
//import com.example.goodTripBackend.features.tour.models.entities.Address;
//import com.example.goodTripBackend.features.user.models.entities.User;
//import com.example.goodTripBackend.features.tour.models.mapper.MapperUtils;
//import com.example.goodTripBackend.features.user.repository.UserRepository;
//import io.imagekit.sdk.ImageKit;
//import io.imagekit.sdk.models.FileCreateRequest;
//import lombok.AllArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@AllArgsConstructor
//public class AudioExcursionService {
//
//    private static final String AUDIO_BASE_PATH = "goodTrip/audio";
//
//    private static final String IMAGE_BASE_PATH = "goodTrip/photo";
//
//    private final AudioExcursionRepository audioExcursionRepository;
//
//    private final AddressRepository addressRepository;
//
//    private final UserRepository userRepository;
//
//    private final UserService userService;
//
//    private final MapperUtils mapperUtils;
//
//    private final MapperAudioExcursion mapperAudioExcursion;
//
////    public Long saveImageFile(Long tourId, MultipartFile image) throws IOException {
////        AudioExcursion audioExcursion = audioExcursionRepository.findById(tourId).orElseThrow();
////        var imagePath = saveImage(image, String.valueOf(audioExcursion.getId()));
////        audioExcursion.setImagePath(imagePath);
////        return audioExcursionRepository.save(audioExcursion).getId();
////    }
////
////    public Long saveAudioFile(Long tourId, MultipartFile audio) throws IOException {
////        AudioExcursion audioExcursion = audioExcursionRepository.findById(tourId).orElseThrow();
////        String audioPath = saveAudio(audio, tourId);
////        audioExcursion.setAudioPath(audioPath);
////        return audioExcursionRepository.save(audioExcursion).getId();
////    }
//
//    public Long save(AudioExcursionDto audioExcursionDto, MultipartFile image, MultipartFile audio, Long userId) throws IOException {
//        var audioExcursion = AudioExcursion.builder()
//                .name(audioExcursionDto.getName())
//                .weekdays(mapperUtils.mapToWeekdays(audioExcursionDto.getWeekdays()))
//                .description(audioExcursionDto.getDescription())
//                .build();
//
//        String savedImage = saveImage(image, String.valueOf(audioExcursion.getId()));
//        audioExcursion.setImagePath(savedImage);
//
//        String savedAudio = saveAudio(audio, String.valueOf(audioExcursion.getId()));
//        audioExcursion.setAudioPath(savedAudio);
//
//        Address address = mapperUtils.mapToAddress(audioExcursionDto.getAddress());
//        addressRepository.save(address);
//        audioExcursion.setAddress(address);
//
//        audioExcursion.setKinds(mapperUtils.mapToTourKinds(audioExcursionDto.getKinds()));
//
//        audioExcursionRepository.save(audioExcursion);
//        userService.addTour(userId, audioExcursion.getId());
//        return audioExcursion.getId();
//    }
//
//    public String saveImage(MultipartFile imageFile, String id) throws IOException {
//        FileCreateRequest fileCreateRequest = new FileCreateRequest(imageFile.getBytes(), IMAGE_BASE_PATH + "/"
//                + id + imageFile.getOriginalFilename());
//        try {
//            return ImageKit.getInstance().upload(fileCreateRequest).getName();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public String saveAudio(MultipartFile audioFile, String id) throws IOException {
//        FileCreateRequest fileCreateRequest = new FileCreateRequest(audioFile.getBytes(), AUDIO_BASE_PATH + "/"
//                + id + audioFile.getOriginalFilename());
//        try {
//            return ImageKit.getInstance().upload(fileCreateRequest).getName();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public boolean checkIsLiked(Long userId, Long tourId) throws Exception {
//        User user = userService.findById(userId);
//        AudioExcursion audioExcursion = audioExcursionRepository.findById(tourId).orElseThrow();
//        return user.getLikedAudioExcursions().contains(audioExcursion);
//    }
//
//    public AudioExcursionDto findById(Long userId, Long id) throws Exception {
//        AudioExcursion audioExcursion = audioExcursionRepository.findById(id).orElseThrow();
//        boolean isLiked = checkIsLiked(userId, id);
//
//        return mapperAudioExcursion.mapToAudioExcursionDto(audioExcursion, isLiked);
//    }
//
//    public List<AudioExcursionDto> findAll(int offset, int limit) {
//        List<AudioExcursion> audioExcursionList = audioExcursionRepository.findAll(PageRequest.of(offset, limit)).getContent();
//
//        return audioExcursionList.stream()
//                .map(mapperAudioExcursion::mapToAudioExcursionDto)
//                .collect(Collectors.toList());
//    }
//
//    public List<AudioExcursionDto> findAllByUser(Long userId, int offset, int limit) throws Exception {
//        User user = userService.findById(userId);
//        List<AudioExcursion> audioExcursionList = user.getCreatedAudioExcursions();
//        List<AudioExcursionDto> audioExcursionDtoList = new ArrayList<>();
//        for (AudioExcursion audioExcursion : audioExcursionList) {
//            boolean isLiked = checkIsLiked(userId, audioExcursion.getId());
//            AudioExcursionDto audioTour = mapperAudioExcursion.mapToAudioExcursionDto(audioExcursion, isLiked);
//            audioExcursionDtoList.add(audioTour);
//        }
//        return audioExcursionDtoList;
//    }
//
//    public List<AudioExcursionDto> findAllByCity(String city, Long userId, int offset, int limit) throws Exception {
//        List<AudioExcursionDto> audioTours = new ArrayList<>();
//        List<AudioExcursion> audioExcursionList = findByCity(city, offset, limit);
//        for (AudioExcursion audioExcursion : audioExcursionList) {
//            boolean isLiked = checkIsLiked(userId, audioExcursion.getId());
//            audioTours.add(mapperAudioExcursion.mapToAudioExcursionDto(audioExcursion, isLiked));
//        }
//        return audioTours;
//    }
//
//    public List<AudioExcursion> findByCity(String city, int offset, int limit) throws Exception {
//        // Получаем страницу адресов по городу
//        Page<Address> addresses = addressRepository.findAllByCity(city, PageRequest.of(offset, limit));
//        List<AudioExcursion> audioExcursionList = new ArrayList<>();
//
//        try {
//            // Проходим по всем адресам на странице
//            for (Address address : addresses.getContent()) {
//                // Находим аудиоэкскурсии по адресу
//                Optional<AudioExcursion> audioExcursionOptional = audioExcursionRepository.findByAddress(address);
//                audioExcursionOptional.ifPresent(audioExcursionList::add);
//            }
//            return audioExcursionList;
//        } catch (Exception e) {
//            // Логируйте ошибку или обрабатывайте её по-другому
//            e.printStackTrace();
//        }
//        return audioExcursionList;
//    }
//
//    public void deleteById(Long userId, Long id) throws Exception {
//        User user = userService.findById(userId);
//        List<AudioExcursion> audioExcursionList = user.getCreatedAudioExcursions();
//        for (AudioExcursion audioExcursion : audioExcursionList) {
//            if (audioExcursion.getId().equals(id)) {
//                audioExcursionRepository.delete(audioExcursion);
//                user.getCreatedAudioExcursions().remove(audioExcursion);
//                userRepository.save(user);
//            }
//        }
//    }
//}