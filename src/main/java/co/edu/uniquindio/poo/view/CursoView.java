package co.edu.uniquindio.poo.view;

import co.edu.uniquindio.poo.controller.CursoController;
import co.edu.uniquindio.poo.enums.EstadoCurso;
import co.edu.uniquindio.poo.enums.Idioma;
import co.edu.uniquindio.poo.enums.NivelReferencia;
import co.edu.uniquindio.poo.enums.TipoBeneficio;
import co.edu.uniquindio.poo.enums.TipoCurso;
import co.edu.uniquindio.poo.patronescreacionales.factorymethod.Curso;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.EnumSet;
import java.util.Set;

public class CursoView extends VistaBase {
    private final CursoController controller = new CursoController();

    private final ComboBox<TipoCurso> cbTipo = new ComboBox<>();
    private final TextField txtCodigo = new TextField();
    private final TextField txtNombre = new TextField();
    private final ComboBox<Idioma> cbIdioma = new ComboBox<>();
    private final TextField txtDescripcion = new TextField();
    private final TextField txtDuracion = new TextField();
    private final TextField txtValorMensual = new TextField();
    private final CheckBox chkPlataforma = new CheckBox("Plataforma virtual");
    private final CheckBox chkMaterial = new CheckBox("Material didáctico");
    private final CheckBox chkClub = new CheckBox("Club de conversación");
    private final TextField txtSesiones = new TextField();
    private final ComboBox<NivelReferencia> cbNivel = new ComboBox<>();
    private final TextField txtObjetivos = new TextField();
    private final ListView<Curso> listaCursos = new ListView<>();
    private final ComboBox<EstadoCurso> cbEstado = new ComboBox<>();
    private final Label lblDetalle = new Label();

    public CursoView() {
        super("Cursos");
        construir();
        refrescar();
    }

    private void construir() {
        cbTipo.getItems().setAll(TipoCurso.values());
        cbIdioma.getItems().setAll(Idioma.values());
        cbNivel.getItems().setAll(NivelReferencia.values());
        cbEstado.getItems().setAll(EstadoCurso.values());
        cbTipo.setOnAction(e -> habilitarCamposPersonalizado());
        habilitarCamposPersonalizado();

        GridPane form = crearFormulario();
        form.addRow(0, new Label("Tipo de curso:"), cbTipo);
        form.addRow(1, new Label("Código:"), txtCodigo, new Label("Nombre:"), txtNombre);
        form.addRow(2, new Label("Idioma:"), cbIdioma, new Label("Descripción:"), txtDescripcion);
        form.addRow(3, new Label("Duración (meses):"), txtDuracion, new Label("Valor mensual:"), txtValorMensual);
        form.add(new Label("Beneficios:"), 0, 4);
        form.add(new HBox(10, chkPlataforma, chkMaterial, chkClub), 1, 4, 3, 1);
        form.addRow(5, new Label("Sesiones con profesor:"), txtSesiones, new Label("Nivel requerido:"), cbNivel);
        form.addRow(6, new Label("Objetivos estudiante:"), txtObjetivos);
        Button btnCrear = new Button("Registrar curso");
        btnCrear.setOnAction(e -> crearCurso());
        Button btnActualizar = new Button("Actualizar seleccionado");
        btnActualizar.setOnAction(e -> actualizarCurso());
        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setOnAction(e -> limpiar());
        form.add(new HBox(10, btnCrear, btnActualizar, btnLimpiar), 1, 7, 3, 1);

        Button btnEstado = new Button("Cambiar estado");
        btnEstado.setOnAction(e -> cambiarEstado());
        HBox estadoBox = new HBox(10, new Label("Nuevo estado del curso seleccionado:"), cbEstado, btnEstado);

        listaCursos.setPrefHeight(160);
        listaCursos.getSelectionModel().selectedItemProperty().addListener((obs, anterior, curso) -> {
            mostrarDetalle(curso);
            cargarEnFormulario(curso);
        });
        lblDetalle.setWrapText(true);

        VBox contenido = new VBox(10, titulo("Registro de cursos"), form, titulo("Cursos registrados"),
                listaCursos, estadoBox, lblDetalle);
        contenido.setPadding(new Insets(10));
        setContenido(contenido);
    }

    private void habilitarCamposPersonalizado() {
        boolean personalizado = cbTipo.getValue() == TipoCurso.PERSONALIZADO;
        txtSesiones.setDisable(!personalizado);
        cbNivel.setDisable(!personalizado);
        txtObjetivos.setDisable(!personalizado);
        if (!personalizado) {
            txtSesiones.clear();
            txtObjetivos.clear();
            cbNivel.setValue(null);
        }
    }

    private void crearCurso() {
        try {
            Set<TipoBeneficio> beneficios = EnumSet.noneOf(TipoBeneficio.class);
            if (chkPlataforma.isSelected()) {
                beneficios.add(TipoBeneficio.PLATAFORMA_VIRTUAL);
            }
            if (chkMaterial.isSelected()) {
                beneficios.add(TipoBeneficio.MATERIAL_DIDACTICO);
            }
            if (chkClub.isSelected()) {
                beneficios.add(TipoBeneficio.CLUB_CONVERSACION);
            }
            Curso curso = controller.crearCurso(cbTipo.getValue(), txtCodigo.getText(), txtNombre.getText(),
                    cbIdioma.getValue(), txtDescripcion.getText(), txtDuracion.getText(),
                    txtValorMensual.getText(), beneficios, txtSesiones.getText(), cbNivel.getValue(),
                    txtObjetivos.getText());
            mostrarInfo("Curso registrado: " + curso.getNombre());
            limpiar();
            refrescar();
        } catch (RuntimeException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void cargarEnFormulario(Curso curso) {
        if (curso == null) {
            return;
        }
        txtCodigo.setText(curso.getCodigo());
        txtNombre.setText(curso.getNombre());
        txtDescripcion.setText(curso.getDescripcion());
        txtValorMensual.setText(String.valueOf(curso.getValorMensual()));
    }

    private void actualizarCurso() {
        Curso seleccionado = listaCursos.getSelectionModel().getSelectedItem();
        try {
            controller.actualizarCurso(seleccionado, txtNombre.getText(), txtDescripcion.getText(),
                    txtValorMensual.getText());
            mostrarInfo("Curso actualizado. Solo se modifican nombre, descripción y valor mensual.");
            refrescar();
            listaCursos.getSelectionModel().select(seleccionado);
        } catch (RuntimeException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void cambiarEstado() {
        try {
            Curso seleccionado = listaCursos.getSelectionModel().getSelectedItem();
            controller.cambiarEstado(seleccionado, cbEstado.getValue());
            refrescar();
            listaCursos.getSelectionModel().select(seleccionado);
        } catch (RuntimeException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void mostrarDetalle(Curso curso) {
        if (curso == null) {
            lblDetalle.setText("");
            return;
        }
        String texto = "Descripción: " + curso.getDescripcion() + "\nDuración: " + curso.getDuracionMeses()
                + " meses | Valor mensual: $" + String.format("%,.0f", curso.getValorMensual())
                + "\nBeneficios: " + (curso.getBeneficios().isEmpty() ? "ninguno" : curso.getBeneficios());
        if (!curso.getDetalleEspecifico().isEmpty()) {
            texto += "\n" + curso.getDetalleEspecifico();
        }
        lblDetalle.setText(texto);
    }

    private void limpiar() {
        listaCursos.getSelectionModel().clearSelection();
        txtCodigo.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        txtDuracion.clear();
        txtValorMensual.clear();
        txtSesiones.clear();
        txtObjetivos.clear();
        chkPlataforma.setSelected(false);
        chkMaterial.setSelected(false);
        chkClub.setSelected(false);
        cbTipo.setValue(null);
        cbIdioma.setValue(null);
        habilitarCamposPersonalizado();
    }

    @Override
    public void refrescar() {
        listaCursos.getItems().setAll(controller.listarCursos());
    }
}
