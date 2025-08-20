package view.utils;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.InputMismatchException;
import java.util.List;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import model.classes.utilitario.Especie;

/**
 *
 * @author herrmann
 */
public class Utils {

    public static <T> void formatTableColumnDate(TableColumn<T, LocalDate> tableColumn) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, LocalDate> cell = new TableCell<T, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
//                        System.out.println("Definir nulo");
                    } else {
                        setText(item.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
                    }
                }
            };
            return cell;
        });
    }

    public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, Double> cell = new TableCell<T, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(NumberFormat.getCurrencyInstance().format(item));
                    }
                }
            };
            return cell;
        });
    }

    public static <T> void formatTableColumnFloat(TableColumn<T, Float> tableColumn) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, Float> cell = new TableCell<T, Float>() {
                @Override
                protected void updateItem(Float item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(NumberFormat.getCurrencyInstance().format(item));
                    }
                }
            };
            return cell;
        });
    }

    public static <T> void formatTableColumnFone(TableColumn<T, String> tableColumn) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, String> cell = new TableCell<T, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null || item.equals("")) {
                        setText(null);
                    } else if (item.length() == 11) {
                        setText("(" + item.substring(0, 2) + ") " + item.substring(2, 7) + " " + item.substring(7));
                    } else {
                        setText("(" + item.substring(0, 2) + ") " + item.substring(2, 6) + " " + item.substring(6));
                    }
                }
            };
            return cell;
        });
    }
    
    public static <T> void formatTableColumnTempoDecorrido(TableColumn<T, String> tableColumn) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, String> cell = new TableCell<T, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null || item.equals("")) {
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                }
            };
            return cell;
        });
    }

    public static <T> void formatTableColumnCnpj(TableColumn<T, String> tableColumn) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, String> cell = new TableCell<T, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item.substring(0, 2) + "." + item.substring(2, 5) + "." + item.substring(5, 8) + "/" + item.substring(8, 12) + "-" + item.substring(12));
                    }
                }
            };
            return cell;
        });
    }

    public static <T> void formatTableColumnCpfOuCnpj(TableColumn<T, String> tableColumn) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, String> cell = new TableCell<T, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else if (item.length() == 14) {
                        setText(item.substring(0, 2) + "." + item.substring(2, 5) + "." + item.substring(5, 8) + "/" + item.substring(8, 12) + "-" + item.substring(12));
                    } else {
                        setText((item.substring(0, 3) + "." + item.substring(3, 6) + "." + item.substring(6, 9) + "-" + item.substring(9, 11)));
                    }
                }
            };
            return cell;
        });
    }
    
    public static <T> void formatTableColumnEspecie(TableColumn<T, Especie> tableColumn) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, Especie> cell = new TableCell<T, Especie>() {
                @Override
                protected void updateItem(Especie item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item.getNome());
                    }
                }
            };
            return cell;
        });
    }

    public static <T> void formatTableColumnPeso(TableColumn<T, Double> tableColumn) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, Double> cell = new TableCell<T, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(NumberFormat.getNumberInstance().format(item) + " kg");
                    }
                }
            };
            return cell;
        });
    }

    public static <T> void formatTableColumnList(TableColumn<T, List> tableColumn) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, List> cell = new TableCell<T, List>() {
                @Override
                protected void updateItem(List item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        if (item.isEmpty()) {
                            setText(null);
                        } else {
                            String string = "";
                            for (int i = 0; i < item.size(); i++) {
                                string += item.get(i);
                                string += i != item.size() - 1 ? ", " : "";
                            }
                            setText(string);
                        }
                    }
                }
            };
            return cell;
        });
    }

    public static <T> void formatTableColumnFaixaRenda(TableColumn<T, Integer> tableColumn) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, Integer> cell = new TableCell<T, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        if (null != item) {
                            switch (item) {
                                case 0:
                                    setText("Até R$ 2.640");
                                    break;
                                case 1:
                                    setText("De R$ 2.640,01 a R$ 4.400");
                                    break;
                                case 2:
                                    setText("De R$ 4.400,01 a R$ 8.000");
                                    break;
                                default:
                                    setText("");
                                    break;
                            }
                        }
                    }
                }
            };
            return cell;
        });
    }

    public static String formataDados(String dado) {
        if (dado == null || dado.equals("")) {
            return "";
        }
        return dado.replaceAll("[^0-9]+", "");
    }

    public static float formataFloat(String dado) {
        if (dado == null || dado.equals("")) {
            return 0;
        }
        dado = dado.replaceAll("[^0-9,\\.]", "").replaceAll(",", ".");
        Float dadoFloat = Float.valueOf(dado);
        return dadoFloat;
    }

    public static boolean isCPF(String CPF) {
        // considera-se erro CPF"s formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000")
                || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")
                || (CPF.length() != 11)) {
            return (false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere "0" no inteiro 0
                // (48 eh a posicao de "0" na tabela ASCII)
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico
            }
            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static String imprimeCPF(String CPF) {
        return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "."
                + CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }

    public static String imprimeCNPJ(String CNPJ) {
        return (CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "."
                + CNPJ.substring(5, 8) + "/" + CNPJ.substring(8, 12)
                + "-" + CNPJ.substring(12));
    }
    
    public static String imprimeCPFouCNPJ(String CPFouCNPJ) {
        if (CPFouCNPJ.length() == 11){
            return (CPFouCNPJ.substring(0, 3) + "." + CPFouCNPJ.substring(3, 6) + "."
                + CPFouCNPJ.substring(6, 9) + "-" + CPFouCNPJ.substring(9, 11));
        }else{
            return (CPFouCNPJ.substring(0, 2) + "." + CPFouCNPJ.substring(2, 5) + "."
                + CPFouCNPJ.substring(5, 8) + "/" + CPFouCNPJ.substring(8, 12)
                + "-" + CPFouCNPJ.substring(12));
        }
    }
    
    public static String imprimePeso(String valor) {
        valor = valor.replace(".", ",");
        if(valor.split(",")[1].length() < 2){
            valor += "0";
        }
        return valor + " kg";
    }
    
    public static String imprimeValor(String valor) {
        valor = valor.replace(".", ",");
        if(valor.split(",")[1].length() < 2){
            valor += "0";
        }
        return valor;
    }
    
    public static String imprimeNumero(Number numero) {
        return String.valueOf(numero).replace(".", ",");
    }
    
    public static String imprimeTelefone(String numero) {
        String numFormatado;
        if(numero.length() == 11){
            numFormatado = "(" + numero.substring(0, 2) + ") " + numero.substring(2, 7) + " " + numero.substring(7);
        }else if (numero.length() == 10){
            numFormatado = "(" + numero.substring(0, 2) + ") " + numero.substring(2, 6) + " " + numero.substring(6);
        }else{
            numFormatado = "";
        }
        return numFormatado;
    }
    
    public static String imprimeCep(String cep) {
        return cep.substring(0, 5) + "-" + cep.substring(5);
    }
    
    public static String imprimeData(LocalDate data){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        return data.format(formatter);
    }
    
    public static String imprimeHora(LocalTime hora){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return hora.format(formatter);
    }
    
    public static String imprimePorcentagem(Number numero){
        String string = String.format("%.1f", numero);
        string = string.replace(".", ",");
        if(string.contains(",") && Integer.parseInt(string.split(",")[1]) == 0){
            return string.split(",")[0] + "%";
        }else{
            return string + "%";
        }
    }
}
