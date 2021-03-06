#!/bin/bash
# copy and download requirements for demo.tar.gz

set -e
LANG=en_US.utf8
ROOT=$(cd "$(dirname "$0")";pwd)
WECROSS_ROOT=${ROOT}/../
BCOS_VERSION=v2.6.0
BCOS_CONSOLE_VERSION=v1.0.10

LOG_INFO()
{
    local content=${1}
    echo -e "\033[32m[INFO] ${content}\033[0m"
}

LOG_ERROR()
{
    local content=${1}
    echo -e "\033[31m[ERROR] ${content}\033[0m"
}

Download()
{
    local url=${1}
    local file=$(basename ${url})
    if [ ! -e ${file} ]; then
        curl -LO ${url}
    fi
}

prepare_bcos()
{
    cd ${ROOT}/bcos/
    # Download
    LOG_INFO "Download build_chain.sh ..."
    Download https://github.com/FISCO-BCOS/FISCO-BCOS/releases/download/${BCOS_VERSION}/build_chain.sh
    chmod u+x build_chain.sh

    LOG_INFO "Download fisco-bcos binary"
    Download https://github.com/FISCO-BCOS/FISCO-BCOS/releases/download/${BCOS_VERSION}/fisco-bcos.tar.gz
    Download https://github.com/FISCO-BCOS/FISCO-BCOS/releases/download/${BCOS_VERSION}/fisco-bcos-macOS.tar.gz

    LOG_INFO "Download tassl requirements"
    Download https://osp-1257653870.cos.ap-guangzhou.myqcloud.com/FISCO-BCOS/FISCO-BCOS/tools/tassl-1.0.2/tassl.tar.gz
    Download https://osp-1257653870.cos.ap-guangzhou.myqcloud.com/FISCO-BCOS/FISCO-BCOS/tools/tassl-1.0.2/tassl_mac.tar.gz

    # LOG_INFO "Download HelloWeCross.sol ..."
    # cp ${WECROSS_ROOT}/src/main/resources/chains-sample/bcos/HelloWeCross.sol ./

    # LOG_INFO "Download FISCO-BCOS console"
    # Download https://github.com/FISCO-BCOS/console/releases/download/${BCOS_CONSOLE_VERSION}/console.tar.gz

    # LOG_INFO "Download ledger-tool ..."
    # git clone --depth 1 https://github.com/Shareong/ledger-tool.git
    # tar -zcf ledger-tool.tar.gz ledger-tool
    # rm -rf ledger-tool

    cd -
}

prepare_fabric()
{
    cd ${ROOT}/fabric/
    # Download
    LOG_INFO "Download fabric tools ..."
    Download https://github.com/hyperledger/fabric/releases/download/v1.4.6/hyperledger-fabric-darwin-amd64-1.4.6.tar.gz
    Download https://github.com/hyperledger/fabric/releases/download/v1.4.6/hyperledger-fabric-linux-amd64-1.4.6.tar.gz

    LOG_INFO "Download fabric samples ..."
    Download https://github.com/hyperledger/fabric-samples/archive/v1.4.4.tar.gz

    cd -
}

prepare_wecross()
{
    cd ${ROOT}
    LOG_INFO "Copy WeCross scripts"
    cp ${WECROSS_ROOT}/scripts/download_wecross.sh ./
    cp ${WECROSS_ROOT}/scripts/download_console.sh ./
}

main()
{
    if [ -n "$1" ]; then
        WECROSS_ROOT=$1/
    fi

    prepare_bcos
    prepare_fabric
    prepare_wecross
}

main $@

