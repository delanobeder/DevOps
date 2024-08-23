from urllib.parse import parse_qs


def parse_query_params(query_string):
    """
        Função para realizar o parse dos parametros.
        """
    query_params = dict(parse_qs(query_string))
    query_params = {k: v[0] for k, v in query_params.items()}
    return query_params
