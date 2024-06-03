import requests

from os import path
from zipfile import ZipFile

script_dir = path.dirname(__file__)
out_dir = path.join(script_dir, "javadocs")
proj_dir = path.join(path.dirname(__file__), "..", "..")
props_path = path.join(path.dirname(__file__), "..", "..", "gradle.properties")

with open(props_path, "r") as f:
    props_data = f.read().splitlines()


def getprop(key: str):
    return [x for x in props_data if x.startswith(key)][0].split("=")[1]


mc_ver = getprop("minecraft_version")
parchment_ver = getprop("parchment_version")
fabric_ver = getprop("fabric_loader_version")
fabric_api_ver = getprop("fabric_api_version")

parchment_url = f"https://maven.parchmentmc.org/org/parchmentmc/data/parchment-{mc_ver}/{parchment_ver}/parchment-{mc_ver}-{parchment_ver}.zip"
fabric_url = f"https://maven.fabricmc.net/net/fabricmc/fabric-loader/{fabric_ver}/fabric-loader-{fabric_ver}-javadoc.jar"
fabric_api_url = f"https://maven.fabricmc.net/net/fabricmc/fabric-api/fabric-api/{fabric_api_ver}/fabric-api-{fabric_api_ver}-fatjavadoc.jar"

fabric_data = requests.get(fabric_url).content
fabric_api_data = requests.get(fabric_api_url).content

with open("fabric_loader_jd.jar", "wb") as f:
    f.write(fabric_data)

with open("fabric_api_jd.jar", "wb") as f:
    f.write(fabric_api_data)

with ZipFile("fabric_loader_jd.jar", "r") as z:
    z.extractall(path.join(out_dir, "fabric-loader"))

with ZipFile("fabric_api_jd.jar", "r") as z:
    z.extractall(path.join(out_dir, "fabric-api"))
