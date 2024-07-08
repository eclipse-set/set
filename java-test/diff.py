import csv
import argparse
import re
from itertools import zip_longest
from jinja2 import Environment, FileSystemLoader
from os import listdir
from os.path import isfile


template = Environment(loader=FileSystemLoader('.')).get_template('diff.html')
def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--diffDir', required=True, type=str)
    diffDir= parser.parse_args().diffDir
    csvFiles = [f for f  in listdir(diffDir) if isfile(diffDir + '/' + f)]
    diffTables= set()
    csvCurrentPattern= re.compile('.+_current.csv')
    for file in csvFiles:
        if (csvCurrentPattern.match(file)):
            diffTables.add(str(file).replace('_current.csv', ''))
        else:
            diffTables.add(str(file).replace('_reference.csv', ''))
    for table in diffTables:
        createDiff(diffDir, table)

def createDiff(diffDir, csvName):
    pathPreFix= diffDir + '/' + csvName
    print('Create diff HTML for:' + csvName)
    with open(pathPreFix + '_current.csv') as current:
        with open(pathPreFix+ '_reference.csv') as reference:
            diff = read_csvs(current, reference)
            with open(pathPreFix + "_diff_out.html", "w") as fh:
                fh.write(template.render(data=diff))

def read_csvs(current, reference):
    current = list(csv.reader(current, delimiter=';', skipinitialspace=True))
    reference = list(csv.reader(reference, delimiter=';', skipinitialspace=True))
    diffView = []

    for crow, rrow in zip_longest(current, reference):
        row = []
        if rrow is None:
            rrow = []
        if crow is None:
            crow = []
        for cdata, rdata in zip_longest(list(crow), list(rrow)):
            cdata.__eq__(rdata)
            if cdata == rdata:
                row.append([cdata])
            else:
                row.append([cdata, rdata])
        diffView.append(row)
    return diffView

main()