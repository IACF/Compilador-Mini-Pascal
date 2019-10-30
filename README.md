# Compilador-Mini-Pascal

## Fases de Desenvolvimento

1. Análise Léxica
	* Obter uma gramática léxica
	* Obter uma expressão regular
	* Implementar o analisador léxico 
	* Testar
2. Análise Sintática
	* Obter uma gramática sintática 
	* Implementar um analisador léxico e sintático usando o método recursivo descendente
	* Integrar ambos
	* Interface gráfica
	* Casos de teste
3. Montagem e Visualização da AST
	* Criar uma estrutura de dados que represente a estrutura sintática do programa fonte, uma árvore
	* Visualização  da árvore usando padrão de projeto _Visitor_
4. Análise de Contexto
	* Descrever dependências de contexto da linguagem
	* Implementar analisador de contexto
5. Geração de Código
	* Implementar geração de código para todos os comandos

### _Todas as fases devem ser extensamente documendas_