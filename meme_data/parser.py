import re

def parser(file):
    dict = {}
    for line in file:
        array = re.split(r'-',line.lower())
        if len(array) > 1:
            key = array[0].rstrip().replace(" ", "-").replace('\'','').replace('.','')
            value = array[1].lstrip().rstrip()
            try:
                dict[key].append(value)
            except KeyError:
                dict[key] = [value]
    return dict