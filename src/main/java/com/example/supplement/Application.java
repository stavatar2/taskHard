package com.example.supplement;

import com.example.supplement.dto.PrescriptionRequestDto;
import com.example.supplement.dto.PrescriptionResponseDto;
import com.example.supplement.dto.SupplementRequestDto;
import com.example.supplement.dto.SupplementResponseDto;
import com.example.supplement.entity.Disease;
import com.example.supplement.entity.Patient;
import com.example.supplement.mapper.PrescriptionMapper;
import com.example.supplement.mapper.SupplementMapper;
import com.example.supplement.repository.DiseaseRepository;
import com.example.supplement.repository.PatientRepository;
import com.example.supplement.repository.PrescriptionRepository;
import com.example.supplement.repository.SupplementRepository;
import com.example.supplement.service.DiseaseService;
import com.example.supplement.service.PatientService;
import com.example.supplement.service.PrescriptionService;
import com.example.supplement.service.SupplementService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class Application {

    private final Scanner scanner = new Scanner(System.in);

    private final SupplementRepository supplementRepository = new SupplementRepository();
    private final DiseaseRepository diseaseRepository = new DiseaseRepository();
    private final PatientRepository patientRepository = new PatientRepository();
    private final PrescriptionRepository prescriptionRepository = new PrescriptionRepository();

    private final SupplementMapper supplementMapper = new SupplementMapper();
    private final PrescriptionMapper prescriptionMapper = new PrescriptionMapper();

    private final SupplementService supplementService = new SupplementService(supplementRepository, supplementMapper);
    private final DiseaseService diseaseService = new DiseaseService(diseaseRepository);
    private final PatientService patientService = new PatientService(patientRepository, diseaseRepository);
    private final PrescriptionService prescriptionService = new PrescriptionService(
        prescriptionRepository,
        patientService,
        supplementService,
        diseaseService,
        prescriptionMapper
    );

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addSupplement();
                case "2" -> addDisease();
                case "3" -> registerPatient();
                case "4" -> assignSupplement();
                case "5" -> showPrescriptions();
                case "0" -> running = false;
                default -> System.out.println("Неизвестный пункт меню");
            }
        }
        System.out.println("Завершение работы");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Добавить пищевую добавку");
        System.out.println("2. Добавить заболевание");
        System.out.println("3. Зарегистрировать пациента");
        System.out.println("4. Назначить добавку пациенту");
        System.out.println("5. Показать все назначения");
        System.out.println("0. Выйти");
        System.out.print("Выбор: ");
    }

    private void addSupplement() {
        System.out.print("Название добавки: ");
        String name = scanner.nextLine().trim();
        int count = readInt("Количество активных веществ: ");
        List<String> ingredients = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            System.out.print("Вещество " + i + ": ");
            String ingredient = scanner.nextLine().trim();
            if (!ingredient.isEmpty()) {
                ingredients.add(ingredient);
            } else {
                i--;
                System.out.println("Название вещества не может быть пустым");
            }
        }
        SupplementRequestDto requestDto = new SupplementRequestDto(name, ingredients);
        SupplementResponseDto responseDto = supplementService.createSupplement(requestDto);
        System.out.println("Добавка сохранена с ID: " + responseDto.getId());
    }

    private void addDisease() {
        System.out.print("Название заболевания: ");
        String name = scanner.nextLine().trim();
        int count = readInt("Количество запрещенных веществ: ");
        Set<String> substances = new HashSet<>();
        for (int i = 1; i <= count; i++) {
            System.out.print("Вещество " + i + ": ");
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                substances.add(value);
            } else {
                i--;
                System.out.println("Название вещества не может быть пустым");
            }
        }
        Disease disease = diseaseService.createDisease(name, substances);
        System.out.println("Заболевание сохранено с ID: " + disease.getId());
    }

    private void registerPatient() {
        System.out.print("Имя пациента: ");
        String name = scanner.nextLine().trim();
        int age = readInt("Возраст: ");
        List<Disease> diseases = diseaseService.findAll();
        if (diseases.isEmpty()) {
            System.out.println("Нет зарегистрированных заболеваний. Пациент будет создан без заболеваний.");
        } else {
            System.out.println("Доступные заболевания:");
            diseases.forEach(disease -> System.out.println(disease.getId() + " - " + disease.getName()));
        }
        System.out.print("Введите ID заболеваний через запятую (или пустую строку): ");
        String diseaseInput = scanner.nextLine().trim();
        List<UUID> diseaseIds = new ArrayList<>();
        if (!diseaseInput.isEmpty()) {
            String[] parts = diseaseInput.split(",");
            for (String part : parts) {
                try {
                    diseaseIds.add(UUID.fromString(part.trim()));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Некорректный ID: " + part + ". Будет пропущен.");
                }
            }
        }
        Patient patient = patientService.registerPatient(name, age, diseaseIds);
        System.out.println("Пациент зарегистрирован с ID: " + patient.getId());
    }

    private void assignSupplement() {
        List<Patient> patients = patientService.findAll();
        if (patients.isEmpty()) {
            System.out.println("Нет пациентов для назначения");
            return;
        }
        List<SupplementResponseDto> supplements = supplementService.listSupplements();
        if (supplements.isEmpty()) {
            System.out.println("Нет добавок для назначения");
            return;
        }
        System.out.println("Пациенты:");
        patients.forEach(patient -> System.out.println(patient.getId() + " - " + patient.getName() + ", возраст: " + patient.getAge()));
        System.out.print("Введите ID пациента: ");
        UUID patientId;
        try {
            patientId = UUID.fromString(scanner.nextLine().trim());
        } catch (IllegalArgumentException ex) {
            System.out.println("Некорректный ID пациента");
            return;
        }
        System.out.println("Добавки:");
        supplements.forEach(supplement -> System.out.println(supplement.getId() + " - " + supplement.getName()));
        System.out.print("Введите ID добавки: ");
        UUID supplementId;
        try {
            supplementId = UUID.fromString(scanner.nextLine().trim());
        } catch (IllegalArgumentException ex) {
            System.out.println("Некорректный ID добавки");
            return;
        }
        PrescriptionRequestDto requestDto = new PrescriptionRequestDto(patientId, supplementId, LocalDate.now());
        try {
            PrescriptionResponseDto responseDto = prescriptionService.createPrescription(requestDto);
            System.out.println("Назначение создано. Статус: " + responseDto.getStatus());
            if (responseDto.getNotes() != null) {
                System.out.println("Комментарий: " + responseDto.getNotes());
            }
        } catch (IllegalArgumentException | IllegalStateException ex) {
            System.out.println("Ошибка: " + ex.getMessage());
        }
    }

    private void showPrescriptions() {
        List<PrescriptionResponseDto> prescriptions = prescriptionService.listPrescriptions();
        if (prescriptions.isEmpty()) {
            System.out.println("Назначений нет");
            return;
        }
        System.out.println("Все назначения:");
        prescriptions.forEach(prescription -> {
            System.out.println("ID: " + prescription.getId());
            System.out.println("Пациент: " + prescription.getPatientName());
            System.out.println("Добавка: " + prescription.getSupplementName());
            System.out.println("Дата: " + prescription.getDate());
            System.out.println("Статус: " + prescription.getStatus());
            if (prescription.getNotes() != null) {
                System.out.println("Комментарий: " + prescription.getNotes());
            }
            System.out.println("---");
        });
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Введите целое число");
            }
        }
    }
}
