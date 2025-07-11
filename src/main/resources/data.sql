-- ==============================================
-- Limpiar tablas
-- ==============================================
DELETE FROM allergy_ingredients;
DELETE FROM allergies;
DELETE FROM ingredients;
DELETE FROM objectives;
DELETE FROM activity_levels;
DELETE FROM recommendations;
DELETE FROM recommendation_templates;

-- ==============================================
-- Seed de Objectives
-- ==============================================
INSERT INTO objectives (id, objective_name, score) VALUES
                                                       (1, 'ganancia',      1),
                                                       (2, 'perdida',      2),
                                                       (3, 'mantenimiento',      3);

-- ==============================================
-- Seed de Ingredients
-- ==============================================
INSERT INTO ingredients (id, name, calories, carbohydrates, proteins, fats, macronutrient_values_id, created_at, updated_at) VALUES
                                                                                                                                 (1, 'nueces', 654.0, 21.0, 15.1, 6.0,  1, now(), now()),
                                                                                                                                 (2, 'tomate',     130.0, 3.5,  1.0, 0.5,  2, now(), now()),
                                                                                                                                 (3, 'cebolla',        40.0,9.0, 1.1, 0.0,  3, now(), now()),
                                                                                                                                 (4, 'Pecanas', 691.0, 14.0, 9.2, 72.0, 3, now(), now()),
                                                                                                                                 (5, 'Leche',    61.0, 4.8, 3.2, 3.3, 3, now(), now()),
                                                                                                                                 (6, 'Queso',   402.0, 1.3, 25.0, 33.0, 3, now(), now());


-- ==============================================
-- Seed de Allergies
-- ==============================================
INSERT INTO allergies (id, name) VALUES
                                     (1, 'Alergia a las nueces'),
                                     (2, 'Intolerance a la lactosa');

-- ==============================================
-- Seed de Allergy_Ingredients
-- ==============================================
INSERT INTO allergy_ingredients (allergy_id, ingredient_name) VALUES
                                                                  (1, 'nueces'),
                                                                  (1, 'pecanas'),
                                                                  (2, 'leche'),
                                                                  (2, 'queso');

-- ==============================================
-- Seed de Activity Levels
-- ==============================================
INSERT INTO activity_levels (id, created_at, updated_at, name, description, activity_factor) VALUES
    (1, now(), now(), 'Sedentario',         'Poco o ningún ejercicio',                                  1.2),
    (2, now(), now(), 'Baja actividad',     'Ejercicio ligero/deporte 1 a 3 días por semana',           1.375),
    (3, now(), now(), 'Moderada actividad', 'Ejercicio moderado/deporte 3 a 5 días por semana',         1.55),
    (4, now(), now(), 'Regular activo',     'Ejercicio intenso/deporte 6 a 7 días por semana',          1.725),
    (5, now(), now(), 'Muy activo',         'Ejercicio muy intenso/trabajo físico o entrenamiento doble', 1.9);


-- ==============================================
-- Seed de Recommendation Templates
-- ==============================================

INSERT INTO recommendation_templates (id, title, content, category) VALUES
    (1, 'Beber más agua', 'Recuerda beber al menos 8 vasos de agua al día para mantenerte hidratado.', 'Salud General'),
    (2, 'Ejercicio regular', 'Realiza al menos 30 minutos de actividad física moderada, cinco días a la semana.', 'Actividad Física'),
    (3, 'Más verduras', 'Incluye al menos una porción de verduras en cada comida.', 'Nutrición'),
    (4, 'Menos comida ultraprocesada', 'Reduce el consumo de alimentos ultraprocesados para mejorar tu salud.', 'Nutrición'),
    (5, 'Dormir bien', 'Procura dormir entre 7 y 8 horas cada noche para una recuperación adecuada.', 'Salud General');



-- ==============================================
-- Seed de Recommendations
-- ==============================================
-- Sin usuario asignado (user_id: NULL), nuevo, notas y razón ficticias, score y status de ejemplo

INSERT INTO recommendations
(id, user_id, template_id, reason, notes, time_of_day, score, status, assigned_at, created_at, updated_at)
VALUES
    (1, NULL, 1, 'Baja hidratación detectada en el perfil.', 'Se recomienda aumentar el consumo de agua.', 'MORNING', 9.5, 'ACTIVE', NULL, now(), now()),
    (2, NULL, 2, 'Reporta poco movimiento semanal.', 'Implementar rutina de caminatas diarias.', 'AFTERNOON', 8.0, 'ACTIVE', NULL, now(), now()),
    (3, NULL, 3, 'Consumo bajo de micronutrientes.', 'Agregar verduras variadas a cada comida.', 'EVENING', 7.0, 'ACTIVE', NULL, now(), now()),
    (4, NULL, 4, 'Ingesta elevada de ultraprocesados.', 'Reducir la cantidad de snacks ultraprocesados.', 'EVENING', 6.5, 'ACTIVE', NULL, now(), now()),
    (5, NULL, 5, 'Indicios de fatiga y sueño irregular.', 'Ajustar horario y condiciones para dormir mejor.', 'EVENING', 9.0, 'ACTIVE', NULL, now(), now());