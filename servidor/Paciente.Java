import java.util.Date;

public class Paciente {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String pais;
    private String estado;
    private String cep;
    private String rua;
    private String bairro;
    private String complemento;
    private String especialidadeMedica;
    private String unidadeClinica;
    private String convenio;
    private Date dataConsulta;
    private String horarioConsulta; 

    // Construtor
    public Paciente(String nome, String cpf, String telefone, String email, String pais,
                    String estado, String cep, String rua, String bairro, String complemento,
                    String especialidadeMedica, String unidadeClinica, String convenio,
                    Date dataConsulta, String horarioConsulta) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.pais = pais;
        this.estado = estado;
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.complemento = complemento;
        this.especialidadeMedica = especialidadeMedica;
        this.unidadeClinica = unidadeClinica;
        this.convenio = convenio;
        this.dataConsulta = dataConsulta;
        this.horarioConsulta = horarioConsulta;
    }


    // Métodos getters

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getPais() {
        return pais;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }

    public String getRua() {
        return rua;
    }

    public String getBairro() {
        return bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getEspecialidadeMedica() {
        return especialidadeMedica;
    }

    public String getUnidadeClinica() {
        return unidadeClinica;
    }

    public String getConvenio() {
        return convenio;
    }

    public Date getDataConsulta() {
        return dataConsulta;
    }

    public String getHorarioConsulta() {
        return horarioConsulta;
    }

    // Métodos setters
    public void setId(int Id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setEspecialidadeMedica(String especialidadeMedica) {
        this.especialidadeMedica = especialidadeMedica;
    }

    public void setUnidadeClinica(String unidadeClinica) {
        this.unidadeClinica = unidadeClinica;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public void setDataConsulta(Date dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public void setHorarioConsulta(String horarioConsulta) {
        this.horarioConsulta = horarioConsulta;
    }
}


