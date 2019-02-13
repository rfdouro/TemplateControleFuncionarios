# TemplateControleFuncionarios
Template usando Spring, Hibernate, Vue, Jquery e Bootstrap
Nesse projeto tenho a adição de um interceptor (JPAInterceptor) para abrir e fechar a conexão 
com a base de dados podendo ser aplicado a um controller ou uma action específica
desde que anotado com @JPAHandler que é uma annotation criada no próprio projeto
O filtro utilizado é para a verificação de login apenas
