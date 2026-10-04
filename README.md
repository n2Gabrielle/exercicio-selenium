# Testes E2E com Selenium - Automation Exercise

Projeto Maven em Java 8 com Selenium WebDriver e JUnit 5 para automatizar os cenários de login com credenciais incorretas (Test Case 3) e cadastro de usuário com exclusão (Test Case 1) no site [Automation Exercise](https://automationexercise.com/).


O WebDriverManager baixa e configura o ChromeDriver automaticamente. Os testes executam o Chrome em modo *headless* otimizado.

## Executar os Testes

Para executar toda a suíte de testes automatizados, utilize o comando Maven no terminal:

```bash
mvn test
```

## Cenários e Análise de Entradas

### Login com email e senha incorreta

O cenário de login incorreto (Test Case 3) utiliza testes parametrizados do JUnit 5 para abranger múltiplas classes de equivalência e análise de valor limite para e-mails não cadastrados. O resultado esperado para todas as entradas é a exibição da mensagem `Your email or password is incorrect!`.


### Cadastro de Usuário

O cenário de registro (Test Case 1) preenche o formulário detalhado de cadastro com dados válidos, utiliza UUID para gerar um e-mail único a cada execução, valida a criação bem-sucedida, confirma o estado logado e realiza a exclusão automática da conta ao final para manter o ambiente limpo.

## Estrutura do Projeto

- `src/test/java/br/com/aulaselenium`: contém os testes E2E automatizados, incluindo a validação parametrizada de login por classes/limites e o fluxo completo de registro, login e exclusão de usuário.
- `pom.xml`: gerenciador de dependências e configuração do projeto Maven (JUnit 5, Selenium WebDriver e WebDriverManager).