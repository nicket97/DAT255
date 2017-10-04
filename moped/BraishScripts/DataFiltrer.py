import json


def filtreur(object,interesting_fields=["height","weight"]):
    data=object.__dict__
    res=dict()
    for key,value in data.items():
        if (key in interesting_fields):
            res[key]=data[key]
    jsonstring = json.dumps(res,ensure_ascii=True)
    return jsonstring
